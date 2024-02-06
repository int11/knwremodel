window.onload = function(){
    const URLSearch = new URLSearchParams(location.search);

    let keyword = URLSearch.get("keyword")
    if (keyword){
        document.getElementById('keyword').value = keyword
    }

    let major = URLSearch.get("major")
    if (major){
        document.getElementById('MajorDropdown').value = major
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
            let table = document.getElementById("mainTable");
            createTable(response.content, table);
            let ul = document.getElementById("mainPageCount");
            ul.replaceChildren()

            let li = document.createElement("li");
            li.style = "float: left;"
            li.innerText = "page"
            ul.appendChild(li)

            for(let i = 0; i<response.totalPages; i++){
                let li = document.createElement("li");
                li.style.float = "left";
                li.style.margin = "0px 5px";

                let a = document.createElement("a");
                a.href = `javascript:paging(${i})`;
                a.text = i+1;


                li.appendChild(a);
                ul.appendChild(li)
            }
        }
    )
}

function loadRankTable(major){
    request(
        "/notice/toplike",
        {major:major, size: 5},
        function (response) {
            let table = document.getElementById("viewRankTable");
            createTable(response, table);
        }
    )

    request(
        "/notice/topView",
        {size: 5},
        function (response) {
            let table = document.getElementById("likeRankTable");
            createTable(response, table);
        }
    )
}

function search(){
    const URLSearch = new URLSearchParams(location.search);
    URLSearch.delete("page");
    URLSearch.set("major", document.getElementById('MajorDropdown').value);
    URLSearch.set("keyword", document.getElementById('keyword').value);
    location.href = location.href.split("?")[0] + '?' + URLSearch.toString();
}

function paging(page){
    const URLSearch = new URLSearchParams(location.search);
    loadNoticeTable(URLSearch.get("major"), URLSearch.get("type"), URLSearch.get("keyword"), page)
}