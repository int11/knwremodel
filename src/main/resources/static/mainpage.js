window.onload = function(){
    const URLSearch = new URLSearchParams(location.search);

    let keyword = URLSearch.get("keyword")
    if (keyword){
        $('#keyword').val(keyword)
    }

    let major = URLSearch.get("major")
    if (major){
        $('#MajorDropdown').val(major)
    }

    loadNoticeTable(URLSearch.get("major"), URLSearch.get("type"), URLSearch.get("keyword"));
    loadRankTable(URLSearch.get("major"));
}

function loadNoticeTable(major="", type="", keyword="", page=0, perPage=20){
    request(
        "/notice/requestPage",
        {major:major,
            type: type,
            keyword:keyword,
            page:page,
            perPage:perPage},
        function (response) {
            let table = $("#mainTable")
            createTable(response.content, table);
            let ul = $("#mainPageCount");
            ul.empty();

            let li = $("<li>")
                .text("page")
                .css({"float": "left"});
            ul.append(li)

            for(let i = 0; i<response.totalPages; i++){
                let li = $("<li>")
                    .css({"float": "left", "margin": "0px 5px"});


                let a = $("<a>")
                    .text(i+1)
                    .attr("href", `javascript:paging(${i})`);
                li.append(a)
                ul.append(li)
            }
        }
    )
}

function loadRankTable(major){
    request(
        "/notice/toplike",
        {major:major, size: 5},
        function (response) {
            let table = $("#viewRankTable");
            createTable(response, table);
        }
    )

    request(
        "/notice/topView",
        {size: 5},
        function (response) {
            let table = $("#likeRankTable");
            createTable(response, table);
        }
    )
}

function search(){
    const URLSearch = new URLSearchParams(location.search);
    URLSearch.delete("page");
    URLSearch.set("major", $('#MajorDropdown').val());
    URLSearch.set("keyword", $('#keyword').val());
    location.href = location.href.split("?")[0] + '?' + URLSearch.toString();
}

function paging(page){
    const URLSearch = new URLSearchParams(location.search);
    loadNoticeTable(URLSearch.get("major"), URLSearch.get("type"), URLSearch.get("keyword"), page)
}