package injea.knwremodel.College;


import jakarta.persistence.*;

@Entity
@Table(name = "colleges")
public class College {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false)
    private String college;

    @Column(nullable = false)
    private String major;

    @Column(nullable = false)
    private String url;

    public College(Long id, String college, String major, String url) {
        this.id = id;
        this.college = college;
        this.major = major;
        this.url = url;
    }

    protected College() {
    }

    public static CollegeBuilder builder() {
        return new CollegeBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public String getCollege() {
        return this.college;
    }

    public String getMajor() {
        return this.major;
    }

    public String getUrl() {
        return this.url;
    }

    public String toString() {
        return "College(id=" + this.getId() + ", college=" + this.getCollege() + ", major=" + this.getMajor() + ", url=" + this.getUrl() + ")";
    }

    public static class CollegeBuilder {
        private Long id;
        private String college;
        private String major;
        private String url;

        CollegeBuilder() {
        }

        public CollegeBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CollegeBuilder college(String college) {
            this.college = college;
            return this;
        }

        public CollegeBuilder major(String major) {
            this.major = major;
            return this;
        }

        public CollegeBuilder url(String url) {
            this.url = url;
            return this;
        }

        public College build() {
            return new College(this.id, this.college, this.major, this.url);
        }

        public String toString() {
            return "College.CollegeBuilder(id=" + this.id + ", college=" + this.college + ", major=" + this.major + ", url=" + this.url + ")";
        }
    }
}

