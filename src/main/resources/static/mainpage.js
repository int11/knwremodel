window.onload = function(){
    const URLSearch = new URLSearchParams(location.search);

    if (URLSearch.get("keyword")){
        document.getElementById('keyword').value = URLSearch.get("keyword");
    }

    if (URLSearch.get("major")){
        document.getElementById('MajorDropdown').value = URLSearch.get("major");
    }
    loadNoticeTable(URLSearch.get("major"), URLSearch.get("type"), URLSearch.get("keyword"));
    loadRankTable(URLSearch.get("major"));
}

function loadNoticeTable(major="", type="", keyword="", page=0, perPage=20){
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
            createTable(response.content, table);
            let ul = document.getElementById("mainPageCount");
            ul.replaceChildren()

            let li = document.createElement("li");
            li.style = "float: left;"
            li.innerText = "page"
            ul.appendChild(li)

            for(let i = 0; i<=response.totalPages; i++){
                let li = document.createElement("li");
                li.style.float = "left";
                li.style.margin = "0px 5px";

                let a = document.createElement("a");
                a.href = `javascript:myopen(${i})`;
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

function myopen(page){
    const URLSearch = new URLSearchParams(location.search);
    loadNoticeTable(URLSearch.get("major"), URLSearch.get("type"), URLSearch.get("keyword"), page)
}

function loadRankTable(major){
    $.ajax({
        url: '/notice/toplike',
        type: 'POST',
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({major:major, size: 5}),
        success: function (response) {
            let table = document.getElementById("viewRankTable");
            createTable(response, table);
        },
        error: function (data) {
            console.error(data.responseText); // 에러 발생 시 콘솔에 출력
        }
    });

    $.ajax({
        url: '/notice/topView',
        type: 'POST',
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({size: 5}),
        success: function (response) {
            let table = document.getElementById("likeRankTable");
            createTable(response, table);
        },
        error: function (data) {
            console.error(data.responseText); // 에러 발생 시 콘솔에 출력
        }
    });
}