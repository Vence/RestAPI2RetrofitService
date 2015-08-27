# RestAPI2RetrofitService
根据RestAPI生成Retrofit相应的Service类，针对大量的API情况下，一键生成 

##前言
这是一款旨在将rest API 自动 转换成retrofit Service 的一个工具；

正如大家所见：**RestAPI 与 RetrofitService  有很多共同点**， 在模块化之后，可以直接通过Restapi按照约定的规则

以下是DomainAPI ， 是restAPI提供给客户端访问数据的接口，使用HTTP协议

	/**
	 * 添加domain
	 * 
	 * @param sessionId  
	 * @param data   domain对象
	 * @return
	 */
	@RequestMapping(value = "/api/{sessionid}/domains" , method = RequestMethod.POST)
	public @ResponseBody
	ResultInfo<Domain> add(@PathVariable("sessionid") String sessionID,
			@RequestParam("data") String data ,
			@RequestParam(value = "deps" , required = false) String deps){

运行该程序后生成出的三个文件对应的内容分别如下：

DomainHttpService: 以下所有内容都从DomainrestAPI中取出，
如 POST方法， 请求URL， 方法名， 参数名，参数类型等等，只不过这里在后面固定加入了一个Callback，表示异步调用接口

 	@POST("/api/{sessionid}/domains")
	 public void add(@Query("deps")String deps,@Query("data")String data,@Path("sessionid")String sessionID,Callback<Domain> cb);

DomainService : 

	 /**
	 * 添加domain
	 * 
	 * @param sessionId  
	 * @param data   domain对象
	 * @return
	 */
	 public void add(String deps,String data,String sessionID,Callback<Domain> cb);

DomainServiceImpl:

	 @Override
	 public void add(String deps,String data,String sessionID,Callback<Domain> cb){
	 	 domainHttpService.add(deps,data,sessionID,cb);
	 }
	 
	 
#使用
MainService是主服务入口，输出方法中两个参数分别是输入路径，和 文件输出路径


	public static void main(String[] args) {
		
		MainService mainService = new MainServiceImpl("D:\\input", "D:\\com\\shenj\\teworksandroid\\http");
		mainService.saveFiles();
	}
	
----
这里需要说明一下，输入路径可以是文件或者文件夹，采用递归的方式将文件夹所有文件进行转换

转换的结果保存在输入路径中，其中用到了pojoName , 这里默认根据文件命名规则从文件名中扣出PojoName 

##其他
之所以开发这样一个小工具，是因为我们的项目用到了Retrofit；

由于文件非常多，而本人又比较懒，就想着这样一劳永逸的方法了，本人测试可用，代码简洁易懂，如果各位业务有变化，可以基于此进行简单二次开发，我开发这个程序只用了二天的时间，我相信二次开发的工作量也不大。

