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

            let noticeTable = $("#notice")
            createTable([Object.fromEntries(obejctlist.slice(0, -4))], noticeTable);

            let bodyTable = $("#body")
            createTable([Object.fromEntries(obejctlist.slice(-4, -1))], bodyTable);

            let commentsTable = $("#comments")
            createTable(Object.fromEntries(obejctlist.slice(-1))["comments"],
                commentsTable,
                function (item, key, cell) {cell.text(item[key])}
            )

            commentsTable.find("tr:not(:first)").each(function() {
                let row = $(this);
                let cell = $(`<td></td>`);
                row.append(cell)
                let button = $(`<button></button>`)
                    .text("댓글 수정")
                    .click(function() {
                            let idCell = row.find('.id');
                            commentModify(idCell.text());
                    });
                cell.append(button)
            });
        }
    )
}

function commentSave() {
    let text = $("#comment").val();
    request(
        "/comments/save",
        {noticeId: noticeId, text: text},
        function (data) {
            console.log(data)
        }
    )
}

function commentModify(commentId) {
    let text = $("#comment").val();
    request(
        "/comments/modify",
        {commentId: commentId, text: text},
        function (data) {console.log(data);}
    );
}

function commentDelete(commentId) {
    request(
        "/comments/delete",
        {commentId: commentId},
        function (data) {console.log(data);}
    );
}