// jquery import 코드
var script = document.createElement('script');
script.src = "https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"; // Check https://jquery.com/ for the current version
document.getElementsByTagName('head')[0].appendChild(script);

function request(url, data, successfunction, errorfunction){
    $.ajax({
        url: url,
        type: 'POST',
        contentType: "application/json",
        data: JSON.stringify(data),
        success: successfunction,
        error: errorfunction
    });

}
function saveDepartment() {
    var department = document.getElementById('department').value;

    // jQuery AJAX를 사용하여 서버에 부서 정보 저장 요청
    $.ajax({
        url: '/user/setDepartment',
        type: 'POST',
        contentType: "application/json",
        data: JSON.stringify({department: department}),
        success: function (response) {
            alert(response); // 서버로부터의 응답을 알림으로 표시
        },
        error: function (xhr, status, error) {
            console.error(xhr.responseText); // 에러 발생 시 콘솔에 출력
        }
    });
}



function createTable(jsonlist, table) {
    table.replaceChildren()
    let tr = table.insertRow();
    for(let item in jsonlist[0]){
        let th = tr.insertCell();
        th.innerText = item; 
    }

    for (let index in jsonlist){
        let tr = table.insertRow();
        let item = jsonlist[index];
        for(let key in item){
            let td = tr.insertCell();
            td.className = key;
            if (key == "isCheckLike"){
                let button = document.createElement("button");
                td.appendChild(button);
                button.onclick = function(){post_clickLike(item["dbid"], button, button.parentNode.parentNode.getElementsByClassName("likeCount")[0]);};
                if (item[key] == false){
                    button.innerText = "좋아요";
                }else{
                    button.innerText = "좋아요 취소";
                }

            }else if(key == "id"){
                let a = document.createElement("a");
                td.appendChild(a);
                a.href = "/read/" + item[key]
                a.innerText = item[key]; 
            }
            else{
                td.innerText = item[key]; 
            }
        }
    }
 }

function setNickname() {
    var nickname = document.getElementById('inputNickname').value;

    $.ajax({
        url: '/user/setNickname',
        type: 'POST',
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({nickname: nickname}),
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
        url: "/mail/send",
        type: "post",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({ "mail": $("#mail").val() }),
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

function mysearch(){
    const URLSearch = new URLSearchParams(location.search);
    URLSearch.delete("page");
    URLSearch.set("major", document.getElementById('MajorDropdown').value);
    URLSearch.set("keyword", document.getElementById('keyword').value);
    location.href = location.href.split("?")[0] + '?' + URLSearch.toString();
}

function post_clickLike(noticeId, target, likecount) {
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
            
            if (target.innerText == "좋아요"){
                likecount.innerText = parseInt(likecount.innerText) + 1;
                target.innerText = "좋아요 취소";
            }else{
                target.innerText = "좋아요";
                likecount.innerText = parseInt(likecount.innerText) - 1;
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