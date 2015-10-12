<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/taglib/common.jsp" %>
<!doctype html>
<!--[if lt IE 7 ]><html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]><html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]><html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]><html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--><html lang="en" class="no-js"> <!--<![endif]-->
<head>
	<%@ include file="/WEB-INF/view/taglib/header.jsp" %>
	<%@ include file="/WEB-INF/view/taglib/layer.jsp" %>
	<%@ include file="/WEB-INF/view/taglib/chubby-stacks.jsp" %>
<script type="text/javascript" src="${ctx_script}/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript">
    // Chosen
    jQuery(document).ready(function() {
    	/* jQuery('#leaveType').chosen({ width: "100%" });
        jQuery('#commentForm .link-reset').click(function(){
            jQuery("#leaveType").trigger("chosen:updated");
        }); */
    });

    jQuery('#commentForm .link-reset').click(function(){
        
    });
</script>
</head>
	<body>
 		<div class="body-wrap">
	 		<div class="content">
			    <!--container-->
			    <div class="container">
			    	<div class="row">
                		<div class="col-md-10 col-md-offset-1 col-sm-12">
                			<!-- row -->
		                    <div class="row">
		                        <div class="col-sm-12">
                					<div class="add-comment styled boxed" id="addcomments">
										<div class="add-comment-title"><br/>请假申请单</div>
										<div class="comment-form">
											<form action="#" method="post" id="dataForm">
												<div class="form-inner">
													<div class="field_select">
														<label for="applyUser" class="label_title">姓名:</label>
														<input type="text" id="applyUser" name="applyUser" value="${currentUser.name}" ${state}/>
													</div>
													<div class="field_text lightPlaceholder">
														<label for="leaveType" class="label_title">请假类型:</label>
														<%-- <select name="leaveType" id="leaveType" multiple data-placeholder="请选择请假类型" ${state}>
									                        <option value="事假" ${leave.leaveType=='事假'?'selected':''}>事假</option>
															<option value="病假" ${leave.leaveType=='病假'?'selected':''}>病假</option>
															<option value="婚假" ${leave.leaveType=='婚假'?'selected':''}>婚假</option>
															<option value="产假" ${leave.leaveType=='产假'?'selected':''}>产假</option>
									                    </select> --%>
									                    <div class="input_styled radiolist inline">
														    <div class="rowRadio">
														        <input type="radio" name="leaveType" value="事假" id="radio1" ${leave.leaveType=='事假'?'checked':''} ${state}/>
														        <label for="radio1">事假</label>
														    </div>
														    <div class="rowRadio">
														        <input type="radio" name="leaveType" value="病假" id="radio2" ${leave.leaveType=='病假'?'checked':''} ${state}/>
														        <label for="radio2">病假</label>
														    </div>
														    <div class="rowRadio">
														        <input type="radio" name="leaveType" value="婚假" id="radio3" ${leave.leaveType=='婚假'?'checked':''} ${state}/>
														        <label for="radio3">婚假</label>
														    </div>
														    <div class="rowRadio">
														        <input type="radio" name="leaveType" value="产假" id="radio4" ${leave.leaveType=='产假'?'checked':''} ${state}/>
														        <label for="radio4">产假</label>
														    </div>
														</div>
													</div>
													<div class="field_text lightPlaceholder">
														<label for="hobbies" class="label_title">开始时间:</label>
														<input type="text" id="startTime_id" name="startTime" value="<fmt:formatDate value="${leave.startTime}" type="both"/>" placeholder="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" ${state}/>
													</div>
													<div class="field_text lightPlaceholder">
														<label for="hobbies" class="label_title">结束时间:</label>
														<input type="text" id="endTime_id" name="endTime" value="<fmt:formatDate value="${leave.endTime}" type="both"/>" placeholder="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" ${state}/>
													</div>
													<div class="field_text lightPlaceholder">
														<label for="hobbies" class="label_title">原因说明:</label>
														<div class="field_text field_textarea">
														    <textarea id="reason" name="reason" placeholder="请简洁明确的填写请假原因" style="height:125px;" ${state}>${leave.reason}</textarea>
														</div>
													</div>
												</div>
												<div class="rowSubmit">
												<a href="javascript:void(0)" class="btn btn-alt" onclick="test()"><span>test</span></a>
													<c:if test="${leave.task.taskDefinitionKey=='createApply'}">
													<a href="javascript:void(0)" class="btn btn-alt" onclick="saveInfo()"><span>保存</span></a>
													<a href="javascript:void(0)" onclick="complete('${leave.task.id}', null)" class="btn btn-alt btn-blue"><span>发送</span></a>
													</c:if>
													<c:if test="${leave.task.taskDefinitionKey=='deptLeaderAudit'}">
													<a href="javascript:void(0)" onclick="complete('${leave.task.id}', [{key: 'deptLeaderPass',value: true,type: 'B'}])" class="btn btn-alt btn-blue"><span>同意</span></a>
													<a href="javascript:void(0)" onclick="complete('${leave.task.id}', [{key: 'deptLeaderPass',value: false,type: 'B'}])" class="btn btn-alt btn-yellow"><span>不同意</span></a>
													</c:if>
													<c:if test="${leave.task.taskDefinitionKey=='modifyApply'}">
													<a href="javascript:void(0)" class="btn btn-alt" onclick="saveInfo()"><span>保存</span></a>
													<a href="javascript:void(0)" onclick="complete('${leave.task.id}', [{key: 'reApply',value: true,type: 'B'}])" class="btn btn-alt btn-blue"><span>再试试</span></a>
													<a href="javascript:void(0)" onclick="complete('${leave.task.id}', [{key: 'reApply',value: false,type: 'B'}])" class="btn btn-alt btn-yellow"><span>不请啦</span></a>
													</c:if>
												</div>
												<input type="hidden" id="id" name="id" value="${leave.id}">
											</form>
										</div>
									</div>
                				</div>
                			</div>
                		</div>
                	</div>
			    </div>
			    <!--/ container -->
			</div>
		</div>
		<script type="text/javascript">
			var layer;
		
	        function saveInfo(){
	        	/* var objSelect = $('#leaveType');
	        	for (var i = 0; i < objSelect.options.length; i++) {
	                if (objSelect.options[i].selected) {
	                    alert(objSelect.options[i].value+'=='+objSelect.options[i].text);
	                }
	            }
	        	return; */
        		loadi = layer.load(2, {time: layerLoadMaxTime});
	        	var url = "${ctx}/oa/leave/save.do";
	        	$.ajax({
	                cache: true,
	                type: "POST",
	                url: url,
	                data: $('#dataForm').serialize(),
	                async: false,
	                error: function(request) {
	                	layer.close(loadi);
	                },
	                success: function(data) {
	                	layer.close(loadi);
	                	layer.msg("操作成功！", {offset: 'rb',icon: 6,shift: 8,time: layerMsgTime});
	                	//goBackToList();
	                }
	            });
	        }
	        
	        /**
			 * 完成任务
			 * @param {Object} taskId
			 */
			function complete(taskId, variables) {
				// 转换JSON为字符串
			    var keys = "", values = "", types = "";
				if (variables) {
					$.each(variables, function(idx) {
						if (keys != "") {
							keys += ",";
							values += ",";
							types += ",";
						}
						keys += this.key;
						values += this.value;
						types += this.type;
					});
				}
				// 发送任务完成请求
        		loadi = layer.load(2, {time: layerLoadMaxTime});
			    $.post('${ctx}/act/task/complete/', {
			    	taskId: taskId,
			        "vars.keys": keys,
			        "vars.values": values,
			        "vars.types": types
			    }, function(data) {
                	layer.close(loadi);
			        layer.msg("发送成功！", {time: layerMsgTime});
			        goBackToList();
			    });
			}
			
			function goBackToList() {
				window.location.href = "${ctx}/oa/leave/list/task.do";
			}
			
			function test() {
				var id = $("#id").val();
				$.ajax({
					type: 'post',
					url: '${ctx}/oa/leave/detail/'+id+'.do',
					data: {},
					dataType: 'json',
					beforeSend: function(XMLHttpRequest){
						alert("before");
					},
					success: function(data, textStatus){
						alert("success");
					},
					error: function(XMLHttpRequest, textStatus, errorThrown){
						alert("error");
					}
				});
			}
		</script>
	</body>
</html>
