package com.juja.jpa.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "jc_region")
@NamedQueries({
        @NamedQuery(name = "Region.DelebeById", query = "delete from Region r where r.regionId =:id"),
        @NamedQuery(name = "Region.Count", query = "select count (r) from Region r")
})
public class Region implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "region_id")
    private Integer regionId;
    @Column(name = "region_name")
    private String regionName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "region", fetch = FetchType.LAZY)
    private List<City> cityList;

    public Region() {
    }

    public Region(Integer regionId) {
        this.regionId = regionId;
    }

    public Region(String regionName) {
        this.regionName = regionName;
    }

    public Region(Integer regionId, String regionName) {
        this.regionId = regionId;
        this.regionName = regionName;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public List<City> getCityList() {
        return cityList;
    }

    public void setCityList(List<City> cityList) {
        this.cityList = cityList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (regionId != null ? regionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Region)) {
            return false;
        }
        Region other = (Region) object;
        if ((this.regionId == null && other.regionId != null) || (this.regionId != null && !this.regionId.equals(other.regionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Region{" +
                "regionId=" + regionId +
                ", regionName='" + regionName + '\'' +
                '}';
    }
}