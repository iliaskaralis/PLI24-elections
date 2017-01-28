/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evotingdb;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author karalis
 */
@Entity
@Table(name = "TBL_VOTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TblVote.findAll", query = "SELECT t FROM TblVote t")
    , @NamedQuery(name = "TblVote.findByPkVoteId", query = "SELECT t FROM TblVote t WHERE t.pkVoteId = :pkVoteId")
    , @NamedQuery(name = "TblVote.findByFldIsInvalid", query = "SELECT t FROM TblVote t WHERE t.fldIsInvalid = :fldIsInvalid")
    , @NamedQuery(name = "TblVote.findByFldIsBlank", query = "SELECT t FROM TblVote t WHERE t.fldIsBlank = :fldIsBlank")})
public class TblVote implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PK_VOTE_ID")
    private Long pkVoteId;
    @Basic(optional = false)
    @Column(name = "FLD_IS_INVALID")
    private Boolean fldIsInvalid;
    @Basic(optional = false)
    @Column(name = "FLD_IS_BLANK")
    private Boolean fldIsBlank;
    @JoinColumn(name = "FK_CANDIDATE_ID", referencedColumnName = "PK_CANDIDATE_ID")
    @ManyToOne
    private TblCandidate fkCandidateId;
    @JoinColumn(name = "FK_ELECTORAL_PERIPHERY_ID", referencedColumnName = "PK_ELECTORAL_PERIPHERY_ID")
    @ManyToOne(optional = false)
    private TblElectoralPeriphery fkElectoralPeripheryId;
    @JoinColumn(name = "FK_POLITICAL_PARTY_ID", referencedColumnName = "PK_PARTY_ID")
    @ManyToOne
    private TblPoliticalParty fkPoliticalPartyId;

    public TblVote() {
    }

    public TblVote(Long pkVoteId) {
        this.pkVoteId = pkVoteId;
    }

    public TblVote(Long pkVoteId, Boolean fldIsInvalid, Boolean fldIsBlank) {
        this.pkVoteId = pkVoteId;
        this.fldIsInvalid = fldIsInvalid;
        this.fldIsBlank = fldIsBlank;
    }

    public Long getPkVoteId() {
        return pkVoteId;
    }

    public void setPkVoteId(Long pkVoteId) {
        this.pkVoteId = pkVoteId;
    }

    public Boolean getFldIsInvalid() {
        return fldIsInvalid;
    }

    public void setFldIsInvalid(Boolean fldIsInvalid) {
        this.fldIsInvalid = fldIsInvalid;
    }

    public Boolean getFldIsBlank() {
        return fldIsBlank;
    }

    public void setFldIsBlank(Boolean fldIsBlank) {
        this.fldIsBlank = fldIsBlank;
    }

    public TblCandidate getFkCandidateId() {
        return fkCandidateId;
    }

    public void setFkCandidateId(TblCandidate fkCandidateId) {
        this.fkCandidateId = fkCandidateId;
    }

    public TblElectoralPeriphery getFkElectoralPeripheryId() {
        return fkElectoralPeripheryId;
    }

    public void setFkElectoralPeripheryId(TblElectoralPeriphery fkElectoralPeripheryId) {
        this.fkElectoralPeripheryId = fkElectoralPeripheryId;
    }

    public TblPoliticalParty getFkPoliticalPartyId() {
        return fkPoliticalPartyId;
    }

    public void setFkPoliticalPartyId(TblPoliticalParty fkPoliticalPartyId) {
        this.fkPoliticalPartyId = fkPoliticalPartyId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkVoteId != null ? pkVoteId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TblVote)) {
            return false;
        }
        TblVote other = (TblVote) object;
        if ((this.pkVoteId == null && other.pkVoteId != null) || (this.pkVoteId != null && !this.pkVoteId.equals(other.pkVoteId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "evotingdb.TblVote[ pkVoteId=" + pkVoteId + " ]";
    }
    
}
