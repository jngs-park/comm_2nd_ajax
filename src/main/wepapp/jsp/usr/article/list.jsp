<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.ll.exam.article.dto.ArticleDto" %>
<%
List<ArticleDto> articles = (List<ArticleDto>)request.getAttribute("articles");
%>

<%@ include file="../common/head.jspf"%>


<section>

    <div class="container px-3 mx-auto">
        <h1 class="font-bold text-lg">고객센터 문의사항 리스트</h1>

        <ul class="mt-5">
            <% for ( ArticleDto article : articles ) { %>
            <li class="flex">
                <a class="w-[40px] hover:underline hover:text-[red]" href="/usr/article/detail/free/<%=article.getId()%>"><%=article.getId()%></a>

                <a class="flex-grow hover:underline hover:text-[red]" href="/usr/article/detail/free/<%=article.getId()%>"><%=article.getTitle()%></a>
                <a onclick="if ( !confirm('정말로 삭제하시겠습니까?') ) return false;" class="hover:underline hover:text-[red] mr-2" href="/usr/article/delete/free/${article.id}?_method=DELETE">삭제</a>
                <a class="hover:underline hover:text-[red]" href="/usr/article/modify/free/<%=article.getId()%>">수정</a>
            </li>
            <% } %>
        </ul>
    </div>
</section>



<%@ include file="../common/foot.jspf"%>