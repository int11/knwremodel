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

function saveDepartment() {
    var department = document.getElementById('department').value;

    // jQuery AJAX를 사용하여 서버에 부서 정보 저장 요청
    $.ajax({
        url: '/user/saveDepartment',
        type: 'POST',
        data: {department: department},
        success: function (response) {
            alert(response); // 서버로부터의 응답을 알림으로 표시
        },
        error: function (xhr, status, error) {
            console.error(xhr.responseText); // 에러 발생 시 콘솔에 출력
        }
    });
}

function setNickname() {
    var nickname = document.getElementById('inputNickname').value;

    $.ajax({
        url: '/user/setNickname',
        type: 'POST',
        dataType: "json",
        data: {nickname: nickname},
        success: function (data) {
            alert(data);
        },
        error: function (data) {
            console.error(data.responseText);
        }
    });
}

function sendNumber() {
    $("#mail_number").css("display", "block");
    $.ajax({
        url: "/mail",
        type: "post",
        dataType: "json",
        data: { "mail": $("#mail").val() },
        success: function (data) {
            alert(data);
        },
        error: function(data){
            alert(data.responseText);
        }
    });
}

function confirmNumber() {
    var number1 = $("#number").val();
    $.ajax({
        url: "/confirmNumber",
        type: "post",
        dataType: "json",
        data: { "enteredNumber": number1 },
        success: function (data) {
            alert(data);
        },
        error: function(data){
            alert(data.responseText);
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

function post_clickLike(noticeId, target) {
    $.ajax({
        url: "/like/click",
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({
            noticeId: noticeId
        }),
        success: function (data) {
            console.log(data)
            var a = target.parentNode.parentNode.lastElementChild;
            if (target.innerText == "좋아요"){
                a.innerText = parseInt(a.innerText) + 1;
                target.innerText = "좋아요 취소";
            }else{
                target.innerText = "좋아요";
                a.innerText = parseInt(a.innerText) - 1;
            }
        },
        error: function(data){
            alert(data.responseText);
        }
    });
}

function commentSave(noticeId) {
    $.ajax({
        url: "/comments/save",
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({
            noticeId: noticeId,
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