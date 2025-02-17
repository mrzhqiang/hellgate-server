package hellgate.common.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.util.ProxyUtils;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.util.Objects;

/**
 * 基础抽象实体。
 * <p>
 * 最基础的抽象实体，包括所有实体使用的自增长 ID 主键和用于乐观锁的版本号。
 * <p>
 * 关于自增长 ID 主键：
 * <p>
 * 很多系统都不再使用自增长的 ID 作为主键，原因是他们认为不利于数据库迁移和高并发使用。
 * <p>
 * 我们之所以无视这些所谓的缺点，是因为基于 JPA 的数据库框架不需要迁移，它可以无缝对接主流数据库。
 * <p>
 * 并且我们的业务数据相对电商领域来说，非常渺小，请求的并发密集度不高。
 * <p>
 * 一个典型的例子就是，冒险岛服务端使用 MySQL 数据库，也是采用自增长 ID 作为主键，完全经受住了考验。
 * <p>
 * 所以完全不用担心电商领域的专家们，总结出来的所谓缺点。
 */
@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 版本号。
     */
    @Version
    private Long version;

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("Entity of type %s with id: %s", this.getClass().getName(), this.id);
    }

    @Override
    public boolean equals(Object o) {
        if (null == o) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!getClass().equals(ProxyUtils.getUserClass(o))) {
            return false;
        }
        BaseEntity that = (BaseEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version);
    }
}
