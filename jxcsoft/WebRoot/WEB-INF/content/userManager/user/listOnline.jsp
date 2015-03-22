<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>当前在线用户</title>
</head>
<body>
    <web:location location="系统维护-当前在线用户" />
    <div class="content">
            <TABLE class="grid-table">
                <tr>
                    <th width="30%">
                        <nobr>
                            用户名称
                        </nobr>
                    </th>
                    <th width="70%">
                        <nobr>
                           登陆时间
                        </nobr>
                    </th>
                </tr>
                <c:forEach items="${onlineUsers}" var="onlineUser" varStatus="status">
                    <tr <c:if test="${status.count%2==0}">class="td_jg"</c:if>>
                        <td>
                            ${onlineUser.key}
                        </td>
                        <td>
                            <fmt:formatDate value="${onlineUser.value}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </td>
                </c:forEach>
            </table>
        </div>
</body>
</html>