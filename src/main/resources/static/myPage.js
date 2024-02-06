window.onload = function(){
    loadLikes()
    loadComments()
}

function loadLikes(){
    request(
        "/user/likes",
        {},
        function (response) {
            let table = document.getElementById("likedTable");
            createTable(response, table);
        }
    )
}

function loadComments(){
    request(
        "/user/comments",
        {},
        function (response) {
            let table = document.getElementById("writedCommentTable");
            createTable(response, table);
        }
    )
}

function sendNumber() {
    request(
        "/mail/send",
        { "mail": $("#mail").val() },
        function (data) {
            // id mail_number content = display: none
            $("#authDiv").css("display", "block");
            alert(data)
        }
    )
}

function confirmNumber() {
    request(
        "/mail/confirmNumber",
        { "authNumber": $("#authNumber").val() },
        function (data) {alert(data)}
    )
}

function setDepartment() {
    request("/user/setDepartment",
        {department: $("#department").val()},
        function (response) {alert(response)}
    )
}


