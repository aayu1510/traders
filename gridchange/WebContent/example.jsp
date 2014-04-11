<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<sj:head jqueryui="true" jquerytheme="ui-lightness" />		
		<title>Grid Example</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
	</head>

	<body>
		<s:url id="remoteurl" action="getgridcrud"  />
		<s:url var="editurl" action="getgridcrud" />
		<sjg:grid 
			autowidth="true"
			footerrow="true"
			hoverrows="true"
			cssStyle="font-size: 12px; font-family: Trebuchet MS;"
			editurl="%{editurl}" 
			id="gridtable" 
			caption="Grid Example"
			href="%{remoteurl}" 
			pager="true" 
			gridModel="gridModel"
			rowList="10,15,20" 
			rowNum="15" 
			rownumbers="true"
			multiselect="true" 
			multiboxonly="true"
			resizable="true" 
			viewrecords="true"
			name="gridModel" 
			navigator="true"
			editinline="false" 
			cellEdit="true" 
			navigatorRefresh="false"
			draggable="true"
			draggableHelper="original"
			draggableCursor="move"
			draggableRevert="invalid"
			draggableZindex="5000"
			draggableContainment="document"
			droppable="true"
			droppableOnDropTopics="ondrop"
			droppableActiveClass="ui-state-active"
    		droppableHoverClass="ui-state-hover"   
			navigatorAddOptions="
			{
				width:350,closeAfterAdd:true,
				resize:false
			}"               	
			navigatorEditOptions="
			{
				width:350,
	            viewPagerButtons:false,
	            recreateForm:true,
	            checkOnUpdate:false,
	            closeAfterEdit:true,
				resize:false
			}"
			navigatorDeleteOptions="
			{
				width:350, 
				closeAfterDelete:true, 
				reloadAfterSubmit:true,
				resize:false
			}"              		
						
			navigatorExtraButtons="
			{
				seperator: 
				{ 
					title : 'seperator'  
				}, 
				alert : 
				{ 
					title : 'Reload', 
					icon: 'ui-icon-refresh', 
					onclick: function(){ window.location.href = 'gridcrud'; }
				}
			}"
		    onSelectRowTopics="rowselect"
		>
			<sjg:gridColumn 
				name="id" 
				index="id" 
				title="Id" 
				editable="true"
				sortable="false" 
				hidden="true" 
			/>
						
			<sjg:gridColumn 
				name="username" 
				index="username"
				editrules="{required:true}"
				editoptions="{number:false, size:3, maxlength :3}"
				title="Username" 
				sortable="true" 
				search="true"
				searchoptions="{sopt:['eq']}"
				editable="true" 
				tabindex="1"
			/>
						
			<sjg:gridColumn 
				name="password" 
				index="password"
				edittype="text"
				editable="true" 
				editrules="{required:true}"
				editoptions="{number:false, size:30, maxlength :30}"
				title="Password" 
				sortable="true"
				search="true" 
				searchoptions="{sopt:['eq']}" 
				tabindex="2"
			/>
			<sjg:gridColumn 
				name="address" 
				index="address"
				edittype="text"
				editable="true" 
				editrules="{required:true}"
				editoptions="{number:false, size:30, maxlength :30}"
				title="address" 
				sortable="true"
				search="true" 
				searchoptions="{sopt:['eq']}" 
				tabindex="2"
			/>
		</sjg:grid>
	</body>
</html>