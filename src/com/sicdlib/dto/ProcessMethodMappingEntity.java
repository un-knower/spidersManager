package com.sicdlib.dto;

import javax.persistence.*;

/**
 * Created by haoyang on 2017/4/24.
 */
@Entity
@Table(name = "process_method_mapping", schema = "socialMind", catalog = "")
public class ProcessMethodMappingEntity {
    private String id;
    private String processId;
    private String methodId;
    private Integer methodOrder;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "process_id")
    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    @Basic
    @Column(name = "method_id")
    public String getMethodId() {
        return methodId;
    }

    public void setMethodId(String methodId) {
        this.methodId = methodId;
    }

    @Basic
    @Column(name = "method_order")
    public Integer getMethodOrder() {
        return methodOrder;
    }

    public void setMethodOrder(Integer methodOrder) {
        this.methodOrder = methodOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProcessMethodMappingEntity that = (ProcessMethodMappingEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (processId != null ? !processId.equals(that.processId) : that.processId != null) return false;
        if (methodId != null ? !methodId.equals(that.methodId) : that.methodId != null) return false;
        if (methodOrder != null ? !methodOrder.equals(that.methodOrder) : that.methodOrder != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (processId != null ? processId.hashCode() : 0);
        result = 31 * result + (methodId != null ? methodId.hashCode() : 0);
        result = 31 * result + (methodOrder != null ? methodOrder.hashCode() : 0);
        return result;
    }
}
