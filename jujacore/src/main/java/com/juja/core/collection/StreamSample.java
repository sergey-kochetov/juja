package com.juja.core.collection;

public class StreamSample {

    enum Role {
        ADMIN, USER, GUEST
    }
    static class User {
        private long id;
        private String name;
        private Role role;

        public User(long id,  Role role, String name) {
            this.id = id;
            this.name = name;
            this.role = role;
        }

        public long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Role getRole() {
            return role;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", role=" + role +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            User user = (User) o;

            if (id != user.id) return false;
            if (!name.equals(user.name)) return false;
            return role == user.role;
        }

        @Override
        public int hashCode() {
            int result = (int) (id ^ (id >>> 32));
            result = 31 * result + name.hashCode();
            result = 31 * result + role.hashCode();
            return result;
        }
    }

}
