package injea.knwremodel.college

import jakarta.persistence.*


@Entity
@Table(name = "colleges")
class College(
    id: Long, college: String, major: String, url: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    val id: Long = id
    
    var college = college
        protected set

    var major = major
        protected set

    var url = url
        protected set
}

