// jquery import 코드
var script = document.createElement('script');
script.src = "https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"; // Check https://jquery.com/ for the current version
document.getElementsByTagName('head')[0].appendChild(script);

window.onload = function(){
    const URLSearch = new URLSearchParams(location.search);

    if (URLSearch.get("keyword")){
        document.getElementById('keyword').value = URLSearch.get("keyword");
    }

    if (URLSearch.get("major")){
        document.getElementById('MajorDropdown').value = URLSearch.get("major")
    }
}

function sendNumber() {
    $("#mail_number").css("display", "block");
    $.ajax({
        url: "/mail",
        type: "post",
        dataType: "text",
        data: { "mail": $("#mail").val() },
        success: function (data) {
            alert(data);
        }
    });
}

function confirmNumber() {
    var number1 = $("#number").val();
    $.ajax({
        url: "/confirmNumber",
        type: "post",
        dataType: "text",
        data: { "enteredNumber": number1 },
        success: function (data) {
            alert(data);
        }
    });
}


function mysearch(){
    const URLSearch = new URLSearchParams(location.search);
    URLSearch.delete("page");
    URLSearch.set("major", document.getElementById('MajorDropdown').value);
    URLSearch.set("keyword", document.getElementById('keyword').value);
    location.href = location.href.split("?")[0] + '?' + URLSearch.toString();
}

function myopen(page=1){
    const URLSearch = new URLSearchParams(location.search);
    URLSearch.set("page", page);
    location.href = location.href.split("?")[0] + '?' + URLSearch.toString();
}

function post_clickLike(noticeId) {
    fetch("http://localhost:8080/like/click", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            noticeId: noticeId
        }),
    }).then((response) => console.log(response));
    setTimeout(function () {
        window.location.href = window.location.href;
    }, 50);
}

function commentSave(noticeId) {
    let comment = document.getElementById("comments").value;
    fetch("http://localhost:8080/comments/save", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            noticeId: noticeId,
            comment: comment
        }),
    }).then((response) => console.log(response));
    setTimeout(function () {
        window.location.href = window.location.href;
    }, 50);
}

function commentModify(commentId) {
    let comment = document.getElementById("comments").value;
    fetch("http://localhost:8080/comments/modify", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            commentId: commentId,
            comment: comment
        }),
    }).then((response) => console.log(response));
    setTimeout(function () {
        window.location.href = window.location.href;
    }, 50);
}

function commentDelete(commentId) {
    fetch("http://localhost:8080/comments/delete", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            commentId: commentId
        }),
    }).then((response) => console.log(response));
    setTimeout(function () {
        window.location.href = window.location.href;
    }, 50);
}

function abbreviateEmail(email) {
    if (email.indexOf('@') > 0) {
        var parts = email.split('@');
        var username = parts[0];
        var obscured = username.substring(0, 2);

        for (var i = 2; i < username.length; i++) {
            obscured += '*';
        }
        return obscured;
    } else {
        return email;
    }
}

document.addEventListener('DOMContentLoaded', function () {
    var emailElements = document.querySelectorAll('.abbreviate-email');

    emailElements.forEach(function (element) {
        var originalEmail = element.textContent;
        element.textContent = abbreviateEmail(originalEmail);
    });
});

