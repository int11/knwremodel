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
    var number1 = $("#number").val();
    $.ajax({
        url: "/mail/confirmNumber",
        type: "post",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({ "enteredNumber": number1 }),
        success: function (data) {
            alert(data);
        },
        error: function(data){
            alert(data.responseText);
        }
    });
}

function saveDepartment() {
    var department = document.getElementById('department').value;

    request("/user/setDepartment",
        {department: department},
        function (response) {alert(response)}
    )
}


