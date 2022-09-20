package com.Udea.Ciclo3.controller;

import com.Udea.Ciclo3.modelos.Employee;
import com.Udea.Ciclo3.modelos.Enterprise;
import com.Udea.Ciclo3.modelos.Profile;
import com.Udea.Ciclo3.modelos.Transaction;
import com.Udea.Ciclo3.repository.TransactionRepository;
import com.Udea.Ciclo3.services.EmployeeService;
import com.Udea.Ciclo3.services.EnterpriseService;
import com.Udea.Ciclo3.services.ProfileService;
import com.Udea.Ciclo3.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;


@org.springframework.stereotype.Controller
public class Controller {

    //Autowired
    @Autowired
    EnterpriseService empresaService;

    @Autowired
    TransactionService transactionService;
    @Autowired
    ProfileService profileService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    TransactionRepository transactionRepository;


    //EMPRESAS
    @GetMapping({"/","ViewEnterprises"})
    public String viewEnterprises(Model model,@ModelAttribute("mensaje")String mensaje){
        List<Enterprise>listaEmpresas=empresaService.getALLEmpresas();
        model.addAttribute("emplist",listaEmpresas);
        model.addAttribute("mensaje",mensaje);
        return "ViewEnterprises";//llamamos al HTML

    }

    @GetMapping("/AddEnterprise")
    public String AddEnterprise(Model model,@ModelAttribute("mensaje")String mensaje){
        Enterprise emp= new Enterprise();
        model.addAttribute("emp",emp);
        model.addAttribute("mensaje",mensaje);
        return "AddEnterprise";

    }

    @PostMapping("/SaveEnterprise")
    public String SaveEnterprise(Enterprise emp, RedirectAttributes redirectAttributes){
        if (empresaService.saveOrUpdateEmpresa(emp)==true){
            redirectAttributes.addFlashAttribute("mensaje","saveOK");
        }
        redirectAttributes.addFlashAttribute("mensaje","saveError");
        return "redirect:/AddEnterprise";
    }

    @GetMapping("/EditEnterprise/{id}")
    public String EditEnterprise(Model model,@PathVariable Long id, @ModelAttribute("mensaje")String mensaje){
        Enterprise emp =empresaService.getEmpresaById(id);
        //creamos el atributo para el modelo que se llame igualmente emp y es el que ira al HTML para llenar o alimentar campos
        model.addAttribute("emp",emp);
        model.addAttribute("mensaje",mensaje);
        return "EditEnterprise";
    }

    @PostMapping("/ToUpdateEnterprise")
    public String ToUpdateEnterprise(@ModelAttribute("emp")Enterprise emp,RedirectAttributes redirectAttributes){
        if(empresaService.saveOrUpdateEmpresa(emp)){
            redirectAttributes.addFlashAttribute("mensaje","updateOK");
            return "redirect:/ViewEnterprises";
        }
        redirectAttributes.addFlashAttribute("mensaje","updateError");
        return "redirect:/EditEnterprise/"+emp.getId();
    }

    @GetMapping ("/DeleteEnterprise/{id}")
    public String DeleteEnterprise(@PathVariable Long id, RedirectAttributes redirectAttributes){
        if (empresaService.deleteEmpresa(id)==true){
            redirectAttributes.addFlashAttribute("mensaje","deleteOK");
            return "redirect:/ViewEnterprises";
        }
        redirectAttributes.addFlashAttribute("mensaje","deleteError");
        return "redirect:/ViewEnterprises";
    }


    //Empleados

    @GetMapping("ViewEmployees")
    public String ViewEmployees(Model model, @ModelAttribute("mensaje")String mensaje){
        List<Employee> listaEmpleados=employeeService.getAllEmployee();
        model.addAttribute("emplelist",listaEmpleados);
        model.addAttribute("mensaje",mensaje);
        return "viewEmployees"; //llamamos al HTML
    }

    @GetMapping("AddEmployee")
    public String NewEmployee(Model model,@ModelAttribute("mensaje")String mensaje){
        Employee empl = new Employee();
        model.addAttribute("empl",empl);
        model.addAttribute("mensaje",mensaje);
        List<Enterprise>listaEmpresas = empresaService.getALLEmpresas();
        model.addAttribute("emprelist",listaEmpresas);
        return"AddEmployee";//llamar al HTMl
    }

    @PostMapping("/SaveEmployee")
    public String saveEmployee(Employee empl, RedirectAttributes redirectAttributes){
        String passEncriptada = passwordEncoder().encode(empl.getPassword());
        empl.setPassword(passEncriptada);

        if (employeeService.saveOrUpdateEmpleado(empl)==true){
            redirectAttributes.addFlashAttribute("mensaje","saveOK");
            return "redirect:ViewEmployees";
        }
        redirectAttributes.addFlashAttribute("mensaje","SaveError");
        return "redirect:/AddEmployee";
    }

    @GetMapping("EditEmployee/{id}")
    public String EditEmployee(Model model,@PathVariable Long id, @ModelAttribute("Mensaje")String mensaje){
        Employee empl= employeeService.getEmployeebyId(id).get();
        //creamos un atributo para el modelo, que se llama igualmente emp y es el que ira al html para llenar o alimentar campos
        model.addAttribute("empl",empl);
        model.addAttribute("mensaje",mensaje);
        List<Enterprise>listaEmpresas=empresaService.getALLEmpresas();
        return "EditEmployee";
    }

    @PostMapping("/ToUpdateEmployee")
    public String ToUpdateEmployee(@ModelAttribute("empl")Employee empl,RedirectAttributes redirectAttributes ){
        Long id = empl.getId();//sacamos el id del objeto empl
        String OldPass= employeeService.getEmployeebyId(id).get().getPassword();//con ese id consultamos la contraseña que ya esta en la base
        if(!empl.getPassword().equals(OldPass)){
            String passEncriptada = passwordEncoder().encode(empl.getPassword());
            empl.setPassword(passEncriptada);
        }
        if(employeeService.saveOrUpdateEmpleado(empl)){
            redirectAttributes.addFlashAttribute("mensaje","updateOK");
            return "redirect:/ViewEmployees";
        }
        redirectAttributes.addFlashAttribute("mensaje","updateError");
        return "redirect:/EditEmployee/"+empl.getId();

    }


    @GetMapping("DeleteEmployee/{id}")
    public String DeleteEmployee(@PathVariable Long id, RedirectAttributes redirectAttributes){
        if (employeeService.deleteEmployee(id)){
            redirectAttributes.addFlashAttribute("mensaje","deleteOk");
            return "redirect:/ViewEmployee";
        }
        redirectAttributes.addFlashAttribute("mensaje","deleteError");
        return "redirect:/ViewEmployees";
    }

    @GetMapping("/Enterprise/{id}/Employees")//filtrar los empleados por empresa
    public String ViewEmployeesByEnterprise(@PathVariable("id")Long id,Model model){
        List<Employee>listaEmpleados= employeeService.obtenerPorEmpresa(id);
        model.addAttribute("emplelist",listaEmpleados);
        return "ViewEmployees";//llamamos al HTML con el emplelist de los empleados filtrados
    }

    //Movimientos

    @RequestMapping("/ViewTransactions")//controlador que nos lleva al template donde veremos todos los movimientos
    public String ViewTransactions(@RequestParam(value="pagina",required = false,defaultValue = "1")int NumeroPagina,
                                   @RequestParam(value = "medida",required = false,defaultValue = "5")int medida,
                                   Model model, @ModelAttribute("mensaje")String mensaje){
        Page<Transaction> paginaMovimientos = transactionRepository.findAll(PageRequest.of(NumeroPagina,medida));
        model.addAttribute("movlist",paginaMovimientos.getContent());
        model.addAttribute("paginas",new int[paginaMovimientos.getTotalPages()]);
        model.addAttribute("paginaActual",NumeroPagina);
        model.addAttribute("mensaje",mensaje);
        Long sumaMonto=transactionService.ObtenerSumaMontos();
        model.addAttribute("sumaMontos",sumaMonto);//mandamos la suma de montos a la plantilla
        return "ViewTransactions";//llamamos al HTML


    }

    @GetMapping("/AddTransaction")//controlador que nos lleva al template donde podremos crear un nuevo movimiento
    public String NewTransaction(Model model,@ModelAttribute("mensaje")String mensaje){
        Transaction movimiento = new Transaction();
        model.addAttribute("mov",movimiento);
        model.addAttribute("mensaje",mensaje);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email=auth.getName();
        Long idEmployee =transactionService.IdPorCorreo(email);
        model.addAttribute("idEmpleado",idEmployee);
        return "AddTransaction";//llamar al HTML
    }

    @PostMapping("/SaveTransaction")
    public String SaveTransaction(Transaction mov, RedirectAttributes redirectAttributes){
        if(transactionService.saveOrUpdateTransacion(mov)){
            redirectAttributes.addFlashAttribute("mensaje","saveOK");
            return "redirect:/ViewTransactions";
        }
        redirectAttributes.addFlashAttribute("mensaje","saveError");
        return "redirect:/AddTransaction";
    }

    @GetMapping("/EditTransaction/{id}")
    public String EditTransaction (Model model, @PathVariable Long id, @ModelAttribute("mensaje")String mensaje){
        Transaction mov = transactionService.getTransactionById(id);
        //creamos un atributo para el modelo, que se llame igualmente empl y es el que ira al html para llenar o alimentar campos
        model.addAttribute("mov",mov);
        model.addAttribute("mensaje",mensaje);
        List<Employee>listaEmpleados=employeeService.getAllEmployee();
        model.addAttribute("emplelist",listaEmpleados);
        return "EditTransaction";

    }

    @PostMapping ("/ToUpdateTransaction")
    public String ToUpdateTransaction(@ModelAttribute("mov")Transaction mov, RedirectAttributes redirectAttributes){
        if(transactionService.saveOrUpdateTransacion(mov)){
            redirectAttributes.addFlashAttribute("mensaje","updateOK");
            return "redirect:/ViewTransactions";
        }
        redirectAttributes.addFlashAttribute("mensaje","updateError");
        return "redirect:/EditTransaction/"+mov.getId();


    }
    @GetMapping("/DeleteTransaction/{id}")
    public String DeleteTransaction(@PathVariable Long id, RedirectAttributes redirectAttributes){
        if(transactionService.deleteTransaction(id)){

            redirectAttributes.addFlashAttribute("mensaje","deleteOK") ;
            return "redirect:/ViewTransactions";
        }
        redirectAttributes.addFlashAttribute("mensaje","deleteError");
        return "redirect:/ViewTransacions";
    }

    @GetMapping("/Employee/{id}/Transaction")//Filtro de movimientos por empleados
    public String TransactionsByEmployee(@PathVariable ("id")Long id, Model model){
        List<Transaction> movlist= transactionService.getByEmployee(id);
        model.addAttribute("movlist",movlist);
        Long sumaMonto= transactionService.MontosPorEmpleado(id);
        model.addAttribute("SumaMontos",sumaMonto);
        return "ViewTransactions"; //llamamos al HTML
    }

    @GetMapping ("/Enterprise/{id}/Transactions")//Filtro de movimientos por empresa
    public String TransactionsByEnterprise(@PathVariable("id")Long id, Model model){
        List<Transaction> movlist = transactionService.getByEnterprise(id);
        model.addAttribute("movlist",movlist);
        Long sumaMonto = transactionService.MontosPorEmpresa(id);
        model.addAttribute("SumaMOntos",sumaMonto);
        return "ViewTransactions";//llamamo al HTML
    }

    //controlador que me lleva al template de no autorizado
    @RequestMapping (value="/Denegado")
    public String accesoDenegado(){
        return "accessDenied";
    }


    //profile

    @GetMapping("/profile")
    public List<Profile> verProfile(){
        return profileService.getAllProfile();
    }

    @PostMapping("/profile")
    public Profile guardarProfile(@RequestBody Profile profile){
        return this.profileService.saveOrUpdateProfile(profile);
    }

    @GetMapping(path ="/profile/{id}")
    public Profile profilePorID(@PathVariable("id") Long id){
        return this.profileService.getProfileById(id);
    }

    @PatchMapping("/profile/{id}")
    public Profile actualizarProfile(@PathVariable("id")Long id,@RequestBody Profile profile){
        Profile prf = profileService.getProfileById(id);
        prf.setId(profile.getId());
        prf.setUser(profile.getUser());
        prf.setPhone(profile.getPhone());
        prf.setCreatedAt(profile.getCreatedAt());
        prf.setUpdateAt(profile.getUpdateAt());
        return profileService.saveOrUpdateProfile(prf);
    }

    @DeleteMapping(path="profile/{id}")
    public String DeleteProfile(@PathVariable("id")Long id){
        boolean respuesta = this.profileService.deleteProfile(id);
        if (respuesta){
            return "se elimino la respuesta con id" +id;
        }
        return "no se pudo eliminar empresa con id"+id;
    }

    @GetMapping("/employees/{id}/profile")
    public List<Profile> getProfileByEmployee(@PathVariable("id") Long id){
        return profileService.getByEmployee(id);
    }

    @GetMapping("/enterprises/{id}/profile")
    public ArrayList<Transaction> getProfileByEnterprise(@PathVariable("id") Long id){
        return profileService.getByEnterprise(id);
    }

    //Metodo para encriptar contraseñas
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
