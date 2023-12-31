// jquery import 코드
var script = document.createElement('script');
script.src = "https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"; // Check https://jquery.com/ for the current version
document.getElementsByTagName('head')[0].appendChild(script);


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
            if (key == "checkLike"){
                let button = document.createElement("button");
                button.onclick = function(){post_clickLike(item["dbid"], button, button.parentNode.parentNode.getElementsByClassName("likeCount")[0]);};
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

function myopen(page){
    const URLSearch = new URLSearchParams(location.search);
    loadNoticeTable(URLSearch.get("major"), URLSearch.get("type"), URLSearch.get("keyword"), page)
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

    ------------------------------//게시판
var main = {
    init : function () {
        var _this = this;

        $('#btn-save').on('click', function () {
            _this.save();
        });

        $("#btn-update").on('click', function () {
            _this.update();
        });

        $("#btn-delete").on('click', function () {
            _this.delete();
        });
    },
    save : function () {
        var data = {
            title : $("#title").val(),
            writer : $("#writer").val(),
            content : $("#content").val()
        };

        $.ajax({
            type : 'POST',
            url : '/api/v1/posts',
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function () {
            alert('글이 등록되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update : function () {
        var data = {
            title : $("#title").val(),
            content : $("#content").val()
        };

        var id = $("#id").val();

        $.ajax({
            type : 'PUT',
            url : '/api/v1/posts/' + id,
            dataType : 'json',
            contentType : 'application/json; charset=utf-8',
            data : JSON.stringify(data)
        }).done(function () {
            alert('글이 수정되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete : function () {
        var id = $("#id").val();

        $.ajax({
            type : 'DELETE',
            url : '/api/v1/posts/' + id,
            dataType : 'json',
            contentType : 'application/json; charset=utf-8'
        }).done(function () {
            alert('글이 삭제되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};

main.init();



}