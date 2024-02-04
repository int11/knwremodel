window.onload = function(){
    var currentURL = window.location.href

    var index = currentURL.lastIndexOf('/')
    noticeId = currentURL.substring(index + 1)

    loadBody(noticeId)
}

function loadBody(noticeId){
    request(
        "/notice/requestBody",
        {noticeId: noticeId},
        function (response) {
            // 객체를 배열 객체로 변환
            let obejctlist = Object.entries(response)

            let noticeTable = document.getElementById("notice");
            createTable([Object.fromEntries(obejctlist.slice(0, -3))], noticeTable);

            let bodyTable = document.getElementById("body");
            createTable([Object.fromEntries(obejctlist.slice(-3, -1))], bodyTable);

            let commentsTable = document.getElementById("comments");
            createTable(Object.fromEntries(obejctlist.slice(-1)), commentsTable);
        }
    )
}

function commentSave() {
    let text = document.getElementById("comment").value
    request(
        "/comments/save",
        {noticeId: noticeId, text: text},
        function (data) {console.log(data)}
    )
}

function commentModify(commentId) {
    $.ajax({
        url: "/comments/modify",
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({
            commentId: commentId,
            comment: document.getElementById("comments").value
        }),
        success: function (data) {
            console.log(data)
        },
        error: function(data){
            alert(data.responseText);
        }
    });
    setTimeout(function () {
        window.location.href = window.location.href;
    }, 50);
}

function commentDelete(commentId) {
    $.ajax({
        url: "/comments/delete",
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({
            commentId: commentId
        }),
        success: function (data) {
            console.log(data)
        },
        error: function(data){
            alert(data.responseText);
        }
    });
    setTimeout(function () {
        window.location.href = window.location.href;
    }, 50);
}