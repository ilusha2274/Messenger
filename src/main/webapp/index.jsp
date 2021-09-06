<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
	<head>
        <meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
		<title>Messenger</title>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
		<style>
		   a.nav-link
		   {
			color: #ffffff;
		   }
		</style>
	</head>


<body style = "background-color: #1e1d2f">
<div th:fragment="blockMenu">
<div class="d-flex flex-column flex-shrink-0 p-3 text-white bg-dark" style="width: 280px;">
    <a href="/" class="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-white text-decoration-none">
        <svg class="bi me-2" width="40" height="32"><use xlink:href="#bootstrap"/></svg>
        <span class="fs-4">Messenger</span>
    </a>
    <hr>

    <ul class="nav nav-pills flex-column mb-auto">
        <li class="nav-item">
            <a href="/profile" class="nav-link">
                <svg class="bi me-2" width="16" height="16"></svg>
                Профиль
            </a>
        </li>
        <li>
            <a href="/posts" class="nav-link active">
                <svg class="bi me-2" width="16" height="16"></svg>
                Сообщения
            </a>
        </li>
        <li>
            <a href="/settings" class="nav-link">
                <svg class="bi me-2" width="16" height="16"></svg>
                Настройки
            </a>
        </li>
    </ul>
    <hr>

    <div class="dropdown">
        <a href="" class="d-flex align-items-center text-white text-decoration-none dropdown-toggle" id="dropdownUser1" data-bs-toggle="dropdown" aria-expanded="true">
            <img src="donat.jpg" alt="" width="32" height="32" class="rounded-circle me-2">
            <strong th:text="${title}"></strong>
        </a>
    </div>
</div>
</div>
</body>

</html>
