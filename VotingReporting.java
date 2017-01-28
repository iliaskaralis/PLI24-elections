/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evotingdb;

import java.util.List;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;


/**
 *
 * @author karalis
 */
public class VotingReporting {
    
    protected EntityManager em;
    public VotingReporting(EntityManager em) {
      this.em=em;
    }
    void displayParties(){
        TypedQuery<TblPoliticalParty> listAllParties = em.createQuery("SELECT t FROM TblPoliticalParty t", TblPoliticalParty.class);
        List<TblPoliticalParty> parties = listAllParties.getResultList();
        Comparator<TblPoliticalParty> comp;
        comp = (TblPoliticalParty a, TblPoliticalParty b) -> {
            return (b.getFldTitle().compareTo(a.getFldTitle()));
        };
        Collections.sort(parties, comp.reversed());
        System.out.println();
        System.out.println("\033[1;4;31m Αναφορά κομμάτων για την εκλογική περιφέρεια");
     
        parties.forEach((t) -> {
            System.out.println(t.getFldTitle());
        });
    }
    
    void displayCandidates(){
        System.out.println("\n\033[1;4;31m Αναφορά υποψηφίων");
            TypedQuery<TblCandidate> listAllCandidates = em.createQuery("SELECT c FROM TblCandidate c ORDER BY c.fkPoliticalPartyId, c.fldSurname", TblCandidate.class);
            List<TblCandidate> candidates = listAllCandidates.getResultList();
 
            for (TblCandidate c : candidates){
                System.out.println(c.getFldSurname()+" "+c.getFldName());
            }
        }
    
    void displayVotes(){
        int i, j;
        int party;
        TblPoliticalParty[] Pa = new TblPoliticalParty[2];
        System.out.println("\n\n\033[1;4;31m Αναφορά ψήφων");
        
        TypedQuery<TblPoliticalParty> listAllParties = em.createQuery("SELECT t FROM TblPoliticalParty t", TblPoliticalParty.class);
        List<TblPoliticalParty> parties = listAllParties.getResultList();
        
        TypedQuery listCandidateVotes;
        listCandidateVotes = em.createQuery("SELECT c.fldSurname, c.fldName, COUNT(v.fkPoliticalPartyId),c.fkPoliticalPartyId.pkPartyId FROM TblCandidate c, TblVote v WHERE c.pkCandidateId = v.fkCandidateId.pkCandidateId GROUP BY c.fldSurname, c.fldName, c.fkPoliticalPartyId ORDER BY COUNT(v.fkPoliticalPartyId) DESC" , TblCandidate.class);
        List candidateVote = listCandidateVotes.getResultList();
        
        for (i=0 ; i<2; i++){
                Pa[i]= parties.get(i);
                System.out.println("\033[1;4;35m" + Pa[i].getFldTitle());
                
                if (candidateVote != null){
                for(j=0; j<candidateVote.size(); j++){
                    party = Pa[i].getPkPartyId();
                    if (party == (int)((Object[])candidateVote.get(j))[3]){    
                        String c= (String)((Object[])candidateVote.get(j))[0];
                        String n= (String)((Object[])candidateVote.get(j))[1];
                        Long count= (Long)((Object[])candidateVote.get(j))[2];
                        System.out.println(c +" "+ n +" "+ count +" ,");
                        }
                }
        }
    }
}        
 
    void displayVoteStatistics(){
        System.out.println("\n\033[1;4;31m Αναφορά στατιστικών των εκλογών");
        
        int c=0, le=0, ak=0;
        
        TypedQuery listPartyVotes = em.createQuery("SELECT p.fldTitle, COUNT(v.fkPoliticalPartyId) FROM TblPoliticalParty p, TblVote v WHERE p.pkPartyId = v.fkPoliticalPartyId.pkPartyId GROUP BY p.fldTitle, v.fkPoliticalPartyId ORDER BY COUNT(v.fkPoliticalPartyId)" , TblVote.class);
        List peryVote = listPartyVotes.getResultList();
        
        TypedQuery<TblElectoralPeriphery> electoralP = em.createQuery("SELECT t FROM TblElectoralPeriphery t WHERE t.fldName = 'Χανιά'", TblElectoralPeriphery.class);
        List<TblElectoralPeriphery> electP = electoralP.getResultList();
        
        TblElectoralPeriphery ep = electP.get(0);
        System.out.println("Εκλογική Πeριφέρεια: " + ep.getFldName());
        System.out.println("Οι εγγεγραμμένοι στην εκλογική περιφέρεια είναι: " + ep.getFldRegisteredCitizensCount());
        
        Query listVotes = em.createQuery("SELECT COUNT(v) FROM TblVote v GROUP BY v" , TblVote.class);
        List av = listVotes.getResultList();
        
        for (Object t: av)
            c++;
        //Long count = (Long)av.get(0);
        System.out.println(" Ψήφισαν: " + c);
        
        System.out.println(" Ποσοστό αποχής: " + 2*(ep.getFldRegisteredCitizensCount()- c)+ "%");
        
        Query leukaVotes = em.createQuery("SELECT COUNT(v) FROM TblVote v WHERE v.fldIsBlank=true GROUP BY v" , TblVote.class);
        List leukaL = leukaVotes.getResultList();
        
        for (Object l: leukaL)
            le++;
        System.out.println(" Ποσοστό λευκών: " + 2*le + "%");
        
        Query akuraVotes = em.createQuery("SELECT COUNT(v) FROM TblVote v WHERE v.fldIsInvalid = true GROUP BY v" , TblVote.class);
        List akuraV = akuraVotes.getResultList();
        
        for (Object l: akuraV)
            ak++;
        System.out.println(" Ποσοστό άκυρων: " + 2*ak + "%");
        
        if (peryVote != null){
            for (int i=0; i< peryVote.size(); i++){
                String comma= (String)((Object[])peryVote.get(i))[0];
                Long psifoi= (Long)((Object[])peryVote.get(i))[1];
                System.out.println("Το κόμμα "+comma +" έχει ποσοστό ψήφων: "+ 2*psifoi +"% ");
        }
        
    }
    
  }
    
void displayElected(){
    TypedQuery listCandidateVotes = em.createQuery("SELECT c.fldSurname, c.fldName, COUNT(v.fkPoliticalPartyId) FROM TblCandidate c, TblVote v WHERE c.pkCandidateId = v.fkCandidateId.pkCandidateId GROUP BY c.fldSurname, c.fldName ORDER BY COUNT(v.fkPoliticalPartyId) DESC" , TblCandidate.class);
        List candidateVote = listCandidateVotes.getResultList();
        System.out.println("\n\n\033[1;4;31m Οι εκλεγμένοι υποψήφιοι είναι οι ακόλουθοι: ");
       
        if (candidateVote != null){
            for (int i=0; i< 3; i++){
                String c= (String)((Object[])candidateVote.get(i))[0];
                String n= (String)((Object[])candidateVote.get(i))[1];
                Long count= (Long)((Object[])candidateVote.get(i))[2];
                System.out.println(c +" "+ n +" "+ count +" ,");
            }
        }

    }
    
}
           
