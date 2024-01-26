package injea.knwremodel.College

import jakarta.persistence.*


@Entity
@Table(name = "colleges")
class College {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    var id: Long? = null
        private set

    @Column(nullable = false)
    var college: String? = null
        private set

    @Column(nullable = false)
    var major: String? = null
        private set

    @Column(nullable = false)
    var url: String? = null
        private set

    constructor(id: Long?, college: String?, major: String?, url: String?) {
        this.id = id
        this.college = college
        this.major = major
        this.url = url
    }

    protected constructor()

    override fun toString(): String {
        return "College(id=" + this.id + ", college=" + this.college + ", major=" + this.major + ", url=" + this.url + ")"
    }

    class CollegeBuilder internal constructor() {
        private var id: Long? = null
        private var college: String? = null
        private var major: String? = null
        private var url: String? = null

        fun id(id: Long?): CollegeBuilder {
            this.id = id
            return this
        }

        fun college(college: String?): CollegeBuilder {
            this.college = college
            return this
        }

        fun major(major: String?): CollegeBuilder {
            this.major = major
            return this
        }

        fun url(url: String?): CollegeBuilder {
            this.url = url
            return this
        }

        fun build(): College {
            return College(this.id, this.college, this.major, this.url)
        }

        override fun toString(): String {
            return "College.CollegeBuilder(id=" + this.id + ", college=" + this.college + ", major=" + this.major + ", url=" + this.url + ")"
        }
    }

    companion object {
        fun builder(): CollegeBuilder {
            return CollegeBuilder()
        }
    }
}

