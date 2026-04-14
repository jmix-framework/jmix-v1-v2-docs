package com.company.demo.entity;

import io.jmix.core.FileRef;
import io.jmix.core.entity.FileRefConverter;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.JmixProperty;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@JmixEntity
@Table(name = "PRODUCT")
@Entity
public class Product {

    @JmixGeneratedValue
    @Id
    @Column(name = "ID", nullable = false)
    private UUID id;

    @Version
    @Column(name = "VERSION", nullable = false)
    private Integer version;

    @InstanceName
    @Column(name = "NAME")
    private String name;

    @Column(name = "PARTS")
    @Lob
    private String parts;

    // tag::element-collection[]
    @ElementCollection
    @CollectionTable(name = "PRODUCT_TAGS", joinColumns = @JoinColumn(name = "PRODUCT_ID"))
    @Column(name = "TAG")
    @OrderColumn(name = "TAG_ORDER")
    private List<String> tags;
    // end::element-collection[]

    // tag::file-collection[]
    @Column(name = "FILE", length = 1024)
    @ElementCollection
    @CollectionTable(name = "PRODUCT_FILES", joinColumns = @JoinColumn(name = "PRODUCT_ID"))
    @Convert(converter = FileRefConverter.class)
    private Set<FileRef> files;
    // end::file-collection[]

    @Transient
    @JmixProperty
    private List<ProductPart> partsList;

    public Set<FileRef> getFiles() {
        return files;
    }

    public void setFiles(Set<FileRef> files) {
        this.files = files;
    }


    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<ProductPart> getPartsList() {
        return partsList;
    }

    public void setPartsList(List<ProductPart> partsList) {
        this.partsList = partsList;
    }

    public String getParts() {
        return parts;
    }

    public void setParts(String parts) {
        this.parts = parts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}