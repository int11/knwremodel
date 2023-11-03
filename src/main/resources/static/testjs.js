function post_clickLike(noticeid) {
    fetch("http://localhost:8080/like/click", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            noticeId: noticeid
        }),
    }).then((response) => console.log(response));
    setTimeout(function() {
        window.location.href = window.location.href;
    }, 50); 
}
