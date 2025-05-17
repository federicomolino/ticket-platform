package com.ticket_platform.ticket_platform.Controller;

import com.ticket_platform.ticket_platform.Entity.Categoria;
import com.ticket_platform.ticket_platform.Repository.categoriaRepository;
import com.ticket_platform.ticket_platform.Service.categoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("ticket")
public class categoriaController {

    @Autowired
    private categoriaRepository categoriaRepository;

    @Autowired
    private categoriaService categoriaService;

    @GetMapping("/newCategoria")
    public String showListCategoria(Model model){
        List<Categoria> categoria = categoriaRepository.findAll();
        model.addAttribute("listCategoria", categoria);
        model.addAttribute("formCategoria", new Categoria());
        return "Categoria/AddCategoria";
    }

    @PostMapping("/newCategoria")
    public String newCategoria(@Valid @ModelAttribute("formCategoria") Categoria categoriaForm, BindingResult bindingResult,
                               Model model){
        //Verifico se la categoria esiste già
        List<Categoria> categoria = categoriaRepository.findAll();
        for (Categoria c : categoria){
            if (categoriaForm.getNomeCategoria().equalsIgnoreCase(c.getNomeCategoria())){
                bindingResult.rejectValue("nomeCategoria","ErrorNomeCategoria",
                        "Categoria già presente");
            }
        }

        if (bindingResult.hasErrors()){
            model.addAttribute("listCategoria", categoriaRepository.findAll());
            return "Categoria/AddCategoria";
        }

        categoriaService.addCategoria(categoriaForm);
        return "redirect:/ticket/newCategoria";
    }


    @PostMapping("/delete/categoria/{id}")
    public String cancellaCategoria(@PathVariable("id") Integer id, Model model){

        boolean eliminazioneCorretta = categoriaService.cancellaCategoria(id);
        if (!eliminazioneCorretta){
            model.addAttribute("nomeCategoria","La categoria non può essere cancellata," +
                    " perchè associata ai ticket");
            model.addAttribute("listCategoria", categoriaRepository.findAll());
            model.addAttribute("formCategoria", new Categoria());
            return "Categoria/AddCategoria";
        }

        return "redirect:/ticket/newCategoria";
    }
}
