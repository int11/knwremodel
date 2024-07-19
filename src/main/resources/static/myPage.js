window.onload = function(){
    loadLikes()
    loadComments()
    loadCurrentUser()
}

function loadLikes(){
    request(
        "/user/likes",
        {},
        function (data) {
            let table = $("#likedTable");
            createTable(data, table);
        }
    )
}

function loadComments(){
    request(
        "/user/comments",
        {},
        function (data) {
            let table = $("#writedCommentTable");
            createTable(
                data,
                table,
                function (item, key, cell) {
                    if(key == "noticeId"){
                        let a = $("<a>")
                            .text(item[key])
                            .attr("href", "/read/" + item[key]);
                        cell.append(a)
                    }
                    else{
                        cell.text(item[key]);
                    }
                }
            )
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
        function (data) {

            alert(data)
            loadCurrentUser()
        }
    )
}


