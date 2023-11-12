window.onload = function(){
    const URLSearch = new URLSearchParams(location.search);

    if (URLSearch.get("keyword")){
        document.getElementById('keyword').value = URLSearch.get("keyword");
    }

    if (URLSearch.get("major")){
        document.getElementById('MajorDropdown').value = URLSearch.get("major")
    }
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