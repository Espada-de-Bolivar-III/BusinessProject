package com.Udea.Ciclo3.controller;

import com.Udea.Ciclo3.modelos.*;
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
import java.util.Optional;

import org.springframework.stereotype.Controller;

@Controller
public class ControllerA {

    //Autowired
    @Autowired
    EnterpriseService empresaService;

    @Autowired
    TransactionService transactionService;
    @Autowired
    ProfileService profileService;
    @Autowired
    EmployeeService employeeService;
    private Long id;


    //EMPRESAS
    @GetMapping({"/", "ViewEnterprises"})
    public String verEmpresas(Model model, @ModelAttribute("mensaje") String mensaje) {
        List<Enterprise> listaEmpresas = empresaService.getALLEmpresas();
        model.addAttribute("emplist", listaEmpresas);
        model.addAttribute("mensaje", mensaje);
        return "ViewEnterprises";//llamamos al HTML

    }

    @GetMapping("/AddEnterprise")
    public String agregarEmpresa(Model model, @ModelAttribute("mensaje") String mensaje) {
        Enterprise emp = new Enterprise();
        model.addAttribute("emp", emp);
        model.addAttribute("mensaje", mensaje);
        return "AddEnterprise";

    }

    @PostMapping("/SaveEnterprise")
    public String GuardarEmpresa(Enterprise emp, RedirectAttributes redirectAttributes) {
        if (empresaService.saveOrUpdateEmpresa(emp) == true) {
            redirectAttributes.addFlashAttribute("mensaje", "saveOK");
        }
        redirectAttributes.addFlashAttribute("mensaje", "saveError");
        return "redirect:/AddEnterprise";
    }

    @GetMapping("/EditEnterprise/{id}")
    public String EditarEmpresa(Model model, @PathVariable Long id, @ModelAttribute("mensaje") String mensaje) {
        Enterprise emp = empresaService.getEmpresaById(id).get();
        //creamos el atributo para el modelo que se llame igualmente emp y es el que ira al HTML para llenar o alimentar campos
        model.addAttribute("emp", emp);
        model.addAttribute("mensaje", mensaje);
        return "EditEnterprise";
    }

    @PostMapping("/ToUpdateEnterprise")
    public String ActualizarEmpresa(@ModelAttribute("emp") Enterprise emp, RedirectAttributes redirectAttributes) {
        if (empresaService.saveOrUpdateEmpresa(emp)) {
            redirectAttributes.addFlashAttribute("mensaje", "updateOK");
            return "redirect:/ViewEnterprises";
        }
        redirectAttributes.addFlashAttribute("mensaje", "updateError");
        return "redirect:/EditEnterprise/" + emp.getId();
    }

    @GetMapping("/DeleteEnterprise/{id}")
    public String BorrarEmpresa(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (empresaService.deleteEmpresa(id) == true) {
            redirectAttributes.addFlashAttribute("mensaje", "deleteOK");
            return "redirect:/ViewEnterprises";
        }
        redirectAttributes.addFlashAttribute("mensaje", "deleteError");
        return "redirect:/ViewEnterprises";
    }


    //Empleados

    @GetMapping("/ViewEmployees")
    public String VerEmpleados(Model model, @ModelAttribute("mensaje") String mensaje) {
        List<Employee> listaEmpleados = employeeService.getAllEmployee();
        model.addAttribute("emplelist", listaEmpleados);
        model.addAttribute("mensaje", mensaje);
        return "ViewEmployees"; //llamamos al HTML
    }


    @GetMapping("/AddEmployee")
    public String NuevoEmpleado(Model model, @ModelAttribute("mensaje") String mensaje) {
        Employee empl = new Employee();
        model.addAttribute("empl", empl);
        model.addAttribute("mensaje", mensaje);
        List<Enterprise> listaEmpresas = empresaService.getALLEmpresas();
        model.addAttribute("emprelist", listaEmpresas);
        return "AddEmployee";//llamar al HTMl
    }


    @PostMapping("/SaveEmployee")
    public String GuardarEmpleado(Employee empl, RedirectAttributes redirectAttributes) {
        String passEncriptada = passwordEncoder().encode(empl.getPassword());
        empl.setPassword(passEncriptada);

        if (employeeService.saveOrUpdateEmpleado(empl) == true) {
            redirectAttributes.addFlashAttribute("mensaje", "saveOK");
            return "redirect:ViewEmployees";
        }
        redirectAttributes.addFlashAttribute("mensaje", "SaveError");
        return "redirect:/AddEmployee";
    }

    @GetMapping("EditEmployee/{id}")
    public String EditarEmpleado(Model model, @PathVariable Long id, @ModelAttribute("Mensaje") String mensaje) {
        Employee empl = employeeService.getEmployeebyId(id).get();
        //creamos un atributo para el modelo, que se llama igualmente emp y es el que ira al html para llenar o alimentar campos
        model.addAttribute("empl", empl);
        model.addAttribute("mensaje", mensaje);
        List<Enterprise> listaEmpresas = empresaService.getALLEmpresas();
        model.addAttribute("emprelist", listaEmpresas);
        return "EditEmployee";
    }

    @PostMapping("/ToUpdateEmployee")
    public String ActualizarEmpleado(@ModelAttribute("empl") Employee empl, RedirectAttributes redirectAttributes) {
        Long id = empl.getId();//sacamos el id del objeto empl
        String OldPass = employeeService.getEmployeebyId(id).get().getPassword();//con ese id consultamos la contraseña que ya esta en la base de datos
        if (!empl.getPassword().equals(OldPass)) { //si las constraseñas son diferentes actualiza sino dejar igual
            String passEncriptada = passwordEncoder().encode(empl.getPassword()); //compara la contraseña del empleado en la BD
            empl.setPassword(passEncriptada);
        }
        if (employeeService.saveOrUpdateEmpleado(empl)) {
            redirectAttributes.addFlashAttribute("mensaje", "updateOK");
            return "redirect:/ViewEmployees";
        }
        redirectAttributes.addFlashAttribute("mensaje", "updateError");
        return "redirect:/EditEmployee/" + empl.getId();

    }


    @GetMapping("DeleteEmployee/{id}")
    public String BorrarEmpleado(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (employeeService.deleteEmployee(id) == true) {
            redirectAttributes.addFlashAttribute("mensaje", "deleteOk");
            return "redirect:/ViewEmployees";
        }
        redirectAttributes.addFlashAttribute("mensaje", "deleteError");
        return "redirect:/ViewEmployees";
    }


    @GetMapping("/Enterprise/{id}/Employees")//filtrar los empleados por empresa
    public String VerEmpleadosPorEmpresa(@PathVariable("id") Long id, Model model) {
        List<Employee> listaEmpleados = employeeService.obtenerPorEmpresa(id);
        model.addAttribute("emplelist", listaEmpleados);
        return "ViewEmployees";//llamamos al HTML con el emplelist de los empleados filtrados
    }

    //Movimientos

    @RequestMapping("/ViewTransactions")//controlador que nos lleva al template donde veremos todos los movimientos
    public String VerTransacciones(//@RequestParam(value = "pagina", required = false, defaultValue = "1") int NumeroPagina,
                                   //@RequestParam(value = "medida", required = false, defaultValue = "5") int medida,
                                   Model model, @ModelAttribute("mensaje") String mensaje) {
        List<Transaction> listaMovimientos = transactionService.getAllTransactions(); //Page<Transaction> paginaMovimientos = transactionRepository.findAll(PageRequest.of(NumeroPagina, medida));
        model.addAttribute("movlist", listaMovimientos);
        model.addAttribute("mensaje", mensaje);
        //Long sumaMonto = transactionService.MontosPorEmpleado(transactionService.MontosPorEmpleado(long id));
        //System.out.println("Sumas empleado******* " + sumaMonto);
        Long sumaMonto = null;
        model.addAttribute("SumaMontos", sumaMonto);
        sumaMonto = transactionService.ObtenerSumaMontos();
        model.addAttribute("sumaMontos", sumaMonto);//mandamos la suma de montos a la plantilla
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); //conseguir el correo para alimentar user
        String email = auth.getName();
        Employee employee = employeeService.getEmployeebyEmail(email);
        model.addAttribute("id",employee.getId());
        model.addAttribute("identificadorEmployee",employee.getId().toString());
        return "ViewTransactions";//llamamos al HTML
    }
// Add transaction by employee
    @GetMapping("/AddTransaction/{id}/Transaction")//controlador que nos lleva al template donde podremos crear un nuevo movimiento
    public String NuevaTransaccionEmpleado(@PathVariable("id") Long id,Model model, @ModelAttribute("mensaje") String mensaje) {
        Transaction movimiento = new Transaction();
        model.addAttribute("mov", movimiento);
        model.addAttribute("mensaje", mensaje);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); //conseguir el correo para alimentar user
        String email = auth.getName();
        Employee employee = employeeService.getEmployeebyEmail(email);
        model.addAttribute("id",employee.getId());
        //Integer idEmployee = transactionService.IdPorCorreo(email);
        //model.addAttribute("idEmployee", idEmployee);
        return "AddTransaction";//llamar al HTML
    }

    @PostMapping("/Employee/{id}/Transaction")
    public String GuardarTransaccionEmpleado(@PathVariable("id") Long id,TransactionVM mov, RedirectAttributes redirectAttributes, Model model) {
        // mappear TransactionVM a Transaction
        Transaction transaction = new Transaction();
        transaction.setAmount(mov.getAmount());
        transaction.setConcept(mov.getConcept());
        transaction.setFecha(mov.getFecha());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); //conseguir el correo para alimentar user
        String email = auth.getName();
        Employee employee = employeeService.getEmployeebyEmail(email);
        transaction.setUsuario(employee);
        transaction.setEnterprise(employee.getEnterprise());

        if (transactionService.saveOrUpdateTransacion(transaction)) {
            redirectAttributes.addFlashAttribute("mensaje", "saveOK");
            return "redirect:/Employee/"+employee.getId()+"/Transaction";
        }
        redirectAttributes.addFlashAttribute("mensaje", "saveError");
        return "redirect:/AddTransaction/"+employee.getId()+"/Transaction";
    }

    @PostMapping("/SaveTransaction")
    public String GuardarTransaccion(TransactionVM mov, RedirectAttributes redirectAttributes, Model model) {
        // mappear TransactionVM a Transaction
        Transaction transaction = new Transaction();
        transaction.setAmount(mov.getAmount());
        transaction.setConcept(mov.getConcept());
        transaction.setFecha(mov.getFecha());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); //conseguir el correo para alimentar user
        String email = auth.getName();
        Employee employee = employeeService.getEmployeebyEmail(email);
        transaction.setUsuario(employee);
        transaction.setEnterprise(employee.getEnterprise());

        if (transactionService.saveOrUpdateTransacion(transaction)) {
            redirectAttributes.addFlashAttribute("mensaje", "saveOK");
            return "redirect:/ViewTransactions";
        }
        redirectAttributes.addFlashAttribute("mensaje", "saveError");
        return "redirect:/AddTransaction";
    }

    @GetMapping("/EditTransaction/{id}/Employee/{employeeId}")
    public String editarMovimento(Model model, @PathVariable Long id,@PathVariable Long employeeId, @ModelAttribute("mensaje") String mensaje) {
        Transaction mov = transactionService.getTransactionById(id);
        Optional<Employee> emp = employeeService.getEmployeebyId(employeeId);
        //Creamos un atributo para el modelo, que se llame igualmente empl y es el que ira al html para llenar o alimentar campos
        model.addAttribute("mov", mov);
        model.addAttribute("mensaje", mensaje);
        if(emp.isPresent()){
            model.addAttribute("usuario", emp.get());
        }
        List<Employee> listaEmpleados = employeeService.getAllEmployee();
        //model.addAttribute("emplelist", listaEmpleados);//??????????????????
        return "EditTransaction";
    }

    @PostMapping("/ToUpdateTransaction/{id}/Employee/{employeeId}")
    public String ActualizarTransaccionEmpleado(@PathVariable Long id,@PathVariable Long employeeId,@ModelAttribute("mov") TransactionVM mov, RedirectAttributes redirectAttributes) {

        Transaction transaction = new Transaction();
        transaction.setId(mov.getId());
        transaction.setAmount(mov.getAmount());
        transaction.setConcept(mov.getConcept());
        transaction.setFecha(mov.getFecha());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); //conseguir el correo para alimentar user
        String email = auth.getName();
        Employee employee = employeeService.getEmployeebyEmail(email);
        transaction.setUsuario(employee);
        transaction.setEnterprise(employee.getEnterprise());
        //Luego si va esto
        if (transactionService.saveOrUpdateTransacion(transaction)) {
            redirectAttributes.addFlashAttribute("mensaje", "updateOK");
            return "redirect:/Employee/"+employeeId + "/Transaction";
        }
        redirectAttributes.addFlashAttribute("mensaje", "updateError");
        return "redirect:/EditTransaction/"+id+"/Employee/" + employeeId;


    }
    @PostMapping("/ToUpdateTransaction")
    public String ActualizarTransaccion(@ModelAttribute("mov") TransactionVM mov, RedirectAttributes redirectAttributes) {

        Transaction transaction = new Transaction();
        transaction.setId(mov.getId());
        transaction.setAmount(mov.getAmount());
        transaction.setConcept(mov.getConcept());
        transaction.setFecha(mov.getFecha());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); //conseguir el correo para alimentar user
        String email = auth.getName();
        Employee employee = employeeService.getEmployeebyEmail(email);
        transaction.setUsuario(employee);
        transaction.setEnterprise(employee.getEnterprise());
        //Luego si va esto
        if (transactionService.saveOrUpdateTransacion(transaction)) {
            redirectAttributes.addFlashAttribute("mensaje", "updateOK");
            return "redirect:/ViewTransactions";
        }
        redirectAttributes.addFlashAttribute("mensaje", "updateError");
        return "redirect:/EditTransaction/" + mov.getId();


    }
    @GetMapping("/DeleteTransaction/{id}/Employee/{employeeId}")
    public String BorrarTransaccionEmpleado(@PathVariable Long id,@PathVariable Long employeeId, RedirectAttributes redirectAttributes) {
        Long idusuario = employeeId;
        if (transactionService.deleteTransaction(id)) {
            Optional<Employee> emp = employeeService.getEmployeebyId(employeeId);

            if(emp.isPresent()){
                idusuario = emp.get().getId();
            }
            redirectAttributes.addFlashAttribute("mensaje", "deleteOK");
            return "redirect:/Employee/"+idusuario + "/Transaction";
        }
        redirectAttributes.addFlashAttribute("mensaje", "deleteError");
        return "redirect:/Employee/"+idusuario + "/Transaction";
    }
    @GetMapping("/DeleteTransaction/{id}")
    public String BorrarTransaccion(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (transactionService.deleteTransaction(id)) {

            redirectAttributes.addFlashAttribute("mensaje", "deleteOK");
            return "redirect:/ViewTransactions";
        }
        redirectAttributes.addFlashAttribute("mensaje", "deleteError");
        return "redirect:/ViewTransacions";
    }

    @GetMapping("/Employee/{id}/Transaction")//Filtro de movimientos por empleados
    public String TransaccionPorEmpleado(@PathVariable("id") Long id, Model model) {
        List<Transaction> movlist = transactionService.getByEmployee(id);
        model.addAttribute("movlist", movlist);
        Long sumaMonto = transactionService.MontosPorEmpleado(id);
        System.out.println("Sumas empleado******* " + sumaMonto);
        model.addAttribute("SumaMontos", sumaMonto);
        return "ViewTransactions"; //llamamos al HTML
    }

    @GetMapping("/Enterprise/{id}/Transactions")//Filtro de movimientos por empresa
    public String TransaccionPorEmpresa(@PathVariable("id") Long id, Model model) {
        List<Transaction> movlist = transactionService.getByEnterprise(id);
        model.addAttribute("movlist", movlist);
        Long sumaMonto = transactionService.MontosPorEmpresa(id);
        model.addAttribute("SumaMontos", sumaMonto);
        return "ViewTransactions";//llamamos al HTML
    }

    //controlador que me lleva al template de no autorizado
    @RequestMapping(value = "/Denegado")
    public String accesoDenegado() {
        return "accessDenied";
    }


    //profile

    @GetMapping("/profile")
    public List<Profile> verProfile() {
        return profileService.getAllProfile();
    }

    @PostMapping("/profile")
    public Profile guardarProfile(@RequestBody Profile profile) {
        return this.profileService.saveOrUpdateProfile(profile);
    }

    @GetMapping(path = "/profile/{id}")
    public Profile profilePorID(@PathVariable("id") Long id) {
        return this.profileService.getProfileById(id);
    }

    @PatchMapping("/profile/{id}")
    public Profile actualizarProfile(@PathVariable("id") Long id, @RequestBody Profile profile) {
        Profile prf = profileService.getProfileById(id);
        prf.setId(profile.getId());
        prf.setUser(profile.getUser());
        prf.setPhone(profile.getPhone());
        prf.setCreatedAt(profile.getCreatedAt());
        prf.setUpdateAt(profile.getUpdateAt());
        return profileService.saveOrUpdateProfile(prf);
    }

    @DeleteMapping(path = "profile/{id}")
    public String DeleteProfile(@PathVariable("id") Long id) {
        boolean respuesta = this.profileService.deleteProfile(id);
        if (respuesta) {
            return "se elimino la respuesta con id" + id;
        }
        return "no se pudo eliminar empresa con id" + id;
    }

    @GetMapping("/employees/{id}/profile")
    public List<Profile> getProfileByEmployee(@PathVariable("id") Long id) {
        return profileService.getByEmployee(id);
    }

    @GetMapping("/enterprises/{id}/profile")
    public ArrayList<Transaction> getProfileByEnterprise(@PathVariable("id") Long id) {
        return profileService.getByEnterprise(id);
    }


    //Metodo para encriptar contraseñas en spring boot mediante paswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}



