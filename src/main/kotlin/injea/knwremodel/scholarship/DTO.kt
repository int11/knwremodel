package injea.knwremodel.scholarship

class ScholarshipDTO {
    class Common(scholarship: Scholarship){
        var id = scholarship.id!!
        var regdate = scholarship.regdate
        var columns = scholarship.columns
    }
}
