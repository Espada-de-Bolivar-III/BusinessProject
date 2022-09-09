package com.Udea.Ciclo3.services;
import com.Udea.Ciclo3.modelos.Profile;
import com.Udea.Ciclo3.modelos.Transaction;
import com.Udea.Ciclo3.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileService {
    @Autowired
    ProfileRepository profileRepository;

    public List<Profile> getAllProfile(){
        List<Profile> profileList = new ArrayList<>();
        profileRepository.findAll().forEach(profile -> profileList.add(profile));
        return profileList;

    }

    public Profile getProfileById(Long id) {
        return profileRepository.findById(id).get();
    }

    public Profile saveOrUpdateProfile(Profile profile){
        Profile prf = profileRepository.save(profile);
        return prf;
    }

    public boolean deleteProfile(Long id){
        profileRepository.deleteById(id);
        if (this.profileRepository.findById(id).isPresent()){
            return false;
        }
        return true;
    }

    public ArrayList<Profile> getByEmployee(Long id){
        return profileRepository.findByEmployeeId(id);
    }

    public ArrayList<Transaction> getByEnterprise(Long id){
        return profileRepository.findByEnterpriseId(id);
    }


}
