$("ul.layui-nav-tree-index li.layui-nav-item a.layui-nav-itemed-a").on("click",function(){
	let current = $("ul.layui-nav-tree-index li.layui-nav-itemed");
	let str = $(this).find("img").attr("src");
	let str1 = str.substring(0,str.lastIndexOf("."));
	if(str.indexOf("_cur") != -1){
		$(this).find("img").attr("src",str.replace("_cur",""));
	}else{
		$(this).find("img").attr("src",str1+"_cur.png");
		/*if(current && current.length > 0){
			let cSrc = $("img",$(current)).attr("src");
			let str2 = cSrc.substring(0,str.lastIndexOf("."));
			if(str1 != str2){
			$("img",$(current)).attr("src",cSrc.replace("_cur",""));
			}
		}*/
	}
})


$("ul.layui-nav-tree-orgincon li").on("click",function(e){
	let current = $("ul.layui-nav-tree-orgincon li.layui-nav-itemed-a");
	let str = $(this).find("img").attr("src");
	let str1 = str.substring(0,str.lastIndexOf("."));
	if(str.indexOf("_cur") != -1){
		
	}else{
		$(this).find("img").attr("src",str1+"_cur.png");
		$(this).siblings().find("img").each(function(i,v){
			let emmmm = $(this).attr("src");
			if(emmmm.indexOf("_cur") != -1){
				$(this).attr("src",emmmm.replace("_cur",""));
			}
		})
	}
})