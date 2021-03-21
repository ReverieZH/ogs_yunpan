package com.ogs.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.obs.services.ObsClient;
import com.obs.services.model.*;
import com.obs.services.model.fs.DropFolderRequest;
import com.obs.services.model.fs.RenameRequest;
import com.obs.services.model.fs.RenameResult;
import com.ogs.domain.Files;
import com.ogs.domain.OBS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
* @Description: OBS文件的操作
* @Date:
* @Author:
*/
@Component
public class BucketObjectUtil {
	// 官方帮助文档
	// https://obssdk.obs.cn-north-1.myhuaweicloud.com/apidoc/cn/java/index.html
	// 示例代码
	// https://support.huaweicloud.com/sdk-java-devg-obs/obs_21_0002.html
	@Autowired
	private OBS obs;

	private static String endPoint;
//	private static final String endPoint = "obs.cn-north-4.myhuaweicloud.com";


	private static String ak;
//	private static final String ak = "E9MOTADUKWHLBSPKRBO1";

	private static String sk;
//	private static final String sk = "1cz6rCBX3djJKc9yuTHFaHrBNFuL5nLqgtUtF966";

	private static String bucketName;
//	private static final String bucketName = "obs-ogs-test";

	public ObsClient getInstance() {
		endPoint = obs.getEndPoint();
		ak = obs.getAk();
		sk = obs.getSk();
		bucketName = obs.getBucketName();
		System.out.println("========================:" + endPoint + " " + ak + " " + sk + " " + " " + bucketName + " ");
		return new ObsClient(ak, sk, endPoint);
	}

	public Integer uploadFile(InputStream is,String objectKey) throws IOException {
		// is 对象的流
		ObsClient obsClient = getInstance();
		System.out.println(">>>>>>>>>>>>>>>>>"+objectKey);
		boolean flag = obsClient.doesObjectExist(bucketName,objectKey);
		PutObjectResult result = null;
		// 根据业务需求，决定是否覆盖
		if (flag) {
			return 0;
		} else {
			// 已存在的文件会被覆盖
			result = obsClient.putObject(bucketName, objectKey, is);
		}
		obsClient.close();
		return result.getStatusCode();
	}

	public List<ObsObject> getAllFileInfo() throws IOException {
		ObsClient obsClient = getInstance();
		ObjectListing objectList = obsClient.listObjects(bucketName);
		List<ObsObject> list = objectList.getObjects();
		obsClient.close();
		return list;
	}

	public boolean removeFile(String objectKey) throws IOException {
		ObsClient obsClient = getInstance();
		boolean flag = obsClient.doesObjectExist(bucketName, objectKey);
		DeleteObjectResult result = null;
		if (flag) {
			result = obsClient.deleteObject(bucketName, objectKey);
			if (result != null) {
				obsClient.close();
				return true;
			} else {
				obsClient.close();
				return false;
			}
		} else {
			obsClient.close();
			return false;
		}
	}



	// 获取文件对象---下载
	// 通常在网上你们见到的下载，是从服务器直接下载，现在文件存在obs上，所以下载的业务流程会发生变化
	public ObsObject getFile(String objectKey) {
		ObsClient obsClient = getInstance();
		boolean flag = obsClient.doesObjectExist(bucketName, objectKey);
		if (flag) {
			ObsObject object = obsClient.getObject(bucketName, objectKey);
			return object;
		}
		return null;
	}

	// 预览---授权访问---你们在网上看到的跟这次不一样，因为网上，文件是存在服务器上的，谁都能看，现在是在obs
	// 预览只支持流式文件
	public String preview(String objectKey) throws IOException {
		ObsClient obsClient = getInstance();
		// 300 有效时间
		TemporarySignatureRequest request = new TemporarySignatureRequest(HttpMethodEnum.GET, 300);
		request.setBucketName(bucketName);
		request.setObjectKey(objectKey);
		TemporarySignatureResponse response = obsClient.createTemporarySignature(request);
		obsClient.close();
		return response.getSignedUrl();
	}

	//删除文件夹
	public boolean removeDir(String dirName) throws IOException {
		ObsClient obsClient = getInstance();
		DropFolderRequest dropFolderRequest = new DropFolderRequest(bucketName, dirName);
		TaskProgressStatus taskProgressStatus = obsClient.dropFolder(dropFolderRequest);
		obsClient.close();
		if (taskProgressStatus.getSucceedTaskNum() > 0) {
			return true;
		} else {
			return false;
		}
	}

	//获取文件夹下的所有对象
	public List<ObsObject> getDirObjectList(String dirName) throws IOException {
		ObsClient obsClient = getInstance();
		ListObjectsRequest request = new ListObjectsRequest(bucketName);
         // 设置文件夹对象名"dir/"为前缀
		request.setPrefix(dirName);
		request.setMaxKeys(1000);
		List<ObsObject> obsObjects=new ArrayList<>();
		ObjectListing result;
		do{
			result = obsClient.listObjects(request);
			obsObjects.addAll(result.getObjects());
			/*for (ObsObject obsObject : result.getObjects())
			{
				System.out.println("\t" + obsObject.getObjectKey());
				System.out.println("\t" + obsObject.getOwner());
			}*/
			request.setMarker(result.getNextMarker());
		}while(result.isTruncated());
		obsClient.close();
		return obsObjects;
	}

	//复制对象
	public void copyFileList(String sourceDiNname,String desdDrName) throws IOException {
		ObsClient obsClient=getInstance();
		List<ObsObject> obsObjects=getDirObjectList(sourceDiNname);
		String orgin=obsObjects.get(0).getObjectKey();
		int i = orgin.lastIndexOf('/', (orgin.lastIndexOf("/") - 1));
		ExecutorService executorService = Executors.newFixedThreadPool(20);
		// 执行并发上传
		for (ObsObject obs : obsObjects) {
			executorService.execute(() -> {
				if(FileUtil.isDir(obs.getObjectKey())){
					obsClient.putObject(bucketName, desdDrName+"/"+obs.getObjectKey().substring(i+1), new ByteArrayInputStream(new byte[0]));
				}else{
					obsClient.copyObject(bucketName,obs.getObjectKey(),bucketName,desdDrName+"/"+obs.getObjectKey().substring(i+1));
				}
			});
		}
		// 等待上传完成
		executorService.shutdown();
		while (!executorService.isTerminated()) {
			try {
				executorService.awaitTermination(5, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// 关闭obsClient
		try {
			obsClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	//重命名
	public Integer renameFile(String objectKey,String newObjectKey) throws IOException {
		ObsClient obsClient=getInstance();
		boolean flag = obsClient.doesObjectExist(bucketName, objectKey);
		RenameResult renameResult = null;
		// 根据业务需求，决定是否覆盖
		if (!flag) {
			return 0;
		} else {
			RenameRequest renameRequest=new RenameRequest(bucketName,objectKey,newObjectKey);
			renameResult = obsClient.renameFile(renameRequest);
		}
		obsClient.close();
		return renameResult.getStatusCode();
	}


	public String uploadFileList(String objectPre,String localDirPath) {
		// 遍历待上传的文件夹，获取所有待上传对象
		File file = new File(localDirPath);
		List<File> list= FileUtil.listFiles(file);
		// 创建ObsClient实例
		ObsClient obsClient = new ObsClient(ak, sk, endPoint);
		// 初始化线程池
		ExecutorService executorService = Executors.newFixedThreadPool(20);
        // 执行并发上传
		for (File f : list) {
			executorService.execute(() -> {
				if (f.isDirectory()) {
					// 如果是空文件夹，则在桶内创建对应的空文件夹对象
					String remoteObjectKey = objectPre + f.getPath().substring(localDirPath.length() + 1) + "/";
					obsClient.putObject(bucketName, remoteObjectKey, new ByteArrayInputStream(new byte[0]));
				} else {
					String remoteObjectKey = objectPre + f.getPath().substring(localDirPath.length() + 1);
					obsClient.putObject(bucketName, remoteObjectKey, new File(f.getPath()));
				}
			});
		}
        // 等待上传完成
		executorService.shutdown();
		while (!executorService.isTerminated()) {
			try {
				executorService.awaitTermination(5, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// 关闭obsClient
		try {
			obsClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
