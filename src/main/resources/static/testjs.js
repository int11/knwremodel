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
    var comment = document.getElementById("comments").value;
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
    var comment = document.getElementById("comments").value;
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