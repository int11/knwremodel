window.onload = function(){
    var currentURL = window.location.href

    var index = currentURL.lastIndexOf('/')
    noticeId = currentURL.substring(index + 1)

    loadBody()
    loadComments()
}

function loadBody(){
    request(
        "/notice/findById",
        {noticeId: noticeId},
        function (data) {
            // 객체를 배열 객체로 변환
            let obejctlist = Object.entries(data)

            let noticeTable = $("#notice")
            createTable(Object.fromEntries(obejctlist.slice(0, -4)), noticeTable);

            let bodyTable = $("#body")
            createTable(Object.fromEntries(obejctlist.slice(-4, -1)), bodyTable);


        }
    )
}

function loadComments(){
    request(
        "/comments/findByNoticeId",
        {noticeId: noticeId},
        function (data) {
            let commentsTable = $("#comments")
            createTable(
                data, commentsTable,
                function (item, key, cell) {cell.text(item[key])}
            )

            commentsTable.find("tr:not(:first)").each(function() {
                let row = $(this);
                let cell = $(`<td></td>`);
                row.append(cell)
                let modifyButton = $(`<button></button>`)
                    .text("댓글 수정")
                    .click(function() {
                        let idCell = row.find('.id');
                        commentModify(idCell.text());
                    });
                cell.append(modifyButton)

                let deleteButton = $(`<button></button>`)
                    .text("댓글 삭제")
                    .click(function() {
                        let idCell = row.find('.id');
                        commentDelete(idCell.text());
                    });
                cell.append(deleteButton)
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
            loadComments()
            $("#comment").val('')
        }
    )
}

function commentModify(commentId) {
    let text = $("#comment").val();
    request(
        "/comments/modify",
        {commentId: commentId, text: text},
        function (data) {
            loadComments()
            $("#comment").val('')
        }
    );
}

function commentDelete(commentId) {
    request(
        "/comments/delete",
        {commentId: commentId},
        function (data) {
            loadComments()
            $("#comment").val('')
        }
    );
}