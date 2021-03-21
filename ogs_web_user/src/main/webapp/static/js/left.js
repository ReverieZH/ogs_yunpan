(function(){
			  var navWrap=document.getElementById("side_nav");
			  var nav1s=navWrap.getElementsByTagName("span");
			  var nav2s=navWrap.getElementsByTagName("ul");
			  for(var i=0,len=nav1s.length;i<len;i++){
			   nav1s[i].onclick=(function(i){
			    return function(){
			     for(var j=0;j<len;j++){
			      nav2s[j].style.display="none";
			     }
			     nav2s[i].style.display="block";
			    }
			   })(i)
			  }
			 })()