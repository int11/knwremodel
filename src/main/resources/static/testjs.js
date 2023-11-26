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

    requestPage(URLSearch.get("major"), URLSearch.get("type"), URLSearch.get("keyword"))
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

function requestPage(major="", type="", keyword="", page=1, perPage=20){
    $.ajax({
        url: '/notice/requestPage',
        type: 'POST',
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({major:major,
               type: type, 
               keyword:keyword, 
               page:page, 
               perPage:perPage}),
        success: function (response) {
            let table = document.getElementById("mainTable");
            createTable(response.data, table);
            let ul = document.getElementById("mainPageCount");
            ul.replaceChildren()

            let li = document.createElement("li");
            li.style = "float: left;"
            li.innerText = "page"
            ul.appendChild(li)

            for(let i = 0; i<response.pageSize; i++){
                let li = document.createElement("li");
                li.style.float = "left";
                li.style.margin = "0px 5px";

                let a = document.createElement("a");
                a.href = `javascript:myopen(${i + 1})`;
                a.text = i+1;
                
                
                li.appendChild(a);
                ul.appendChild(li)
            }
        },
        error: function (data) {
            console.error(data.responseText); // 에러 발생 시 콘솔에 출력
        }
    });
}

function createTable(jsonlist, table) {
    table.replaceChildren()
    let tr = document.createElement("tr");
    for(let item in jsonlist[0]){
        let th = document.createElement("th");
        th.innerText = item; 
        tr.appendChild(th); 
    }

    table.append(tr) 
    for (let index in jsonlist){
        let tr = document.createElement("tr");
        let item = jsonlist[index];
        for(let key in item){
            let td = document.createElement("td");

            if (key == "checkLike"){
                let button = document.createElement("button");
                button.onclick = function(){post_clickLike(item["dbid"], button, button.parentNode.parentNode.getElementsByClassName("likeCount")[0]);};
                console.log(item[key])
                if (item[key] == false){
                    button.innerText = "좋아요";
                }else{
                    button.innerText = "좋아요 취소";
                }
                td.appendChild(button);
            }else{
                td.className = key;
                td.innerText = item[key]; 
            }

            tr.appendChild(td);
        }
        table.appendChild(tr); 
    }
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
        url: "/mail/send",
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
        url: "/mail/confirmNumber",
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

function myopen(page){
    const URLSearch = new URLSearchParams(location.search);
    requestPage(URLSearch.get("major"), URLSearch.get("type"), URLSearch.get("keyword"), page)
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