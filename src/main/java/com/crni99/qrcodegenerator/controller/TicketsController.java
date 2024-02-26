package com.crni99.qrcodegenerator.controller;

import java.io.IOException;

import com.crni99.qrcodegenerator.model.Partits;
import com.crni99.qrcodegenerator.model.Tickets;
import com.crni99.qrcodegenerator.repository.PartitsRepository;
import com.crni99.qrcodegenerator.repository.TicketsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.crni99.qrcodegenerator.service.QRCodeService;

import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.UUID;

@Controller
public class TicketsController {

	private final QRCodeService qrCodeService;
	private final TicketsRepository ticketsRepository;
	private final PartitsRepository repository;

	@Autowired
	public TicketsController(QRCodeService qrCodeService, TicketsRepository ticketsRepository, PartitsRepository repository) {
		this.qrCodeService = qrCodeService;
		this.ticketsRepository = ticketsRepository;
		this.repository = repository;
	}
	@GetMapping("/tickets/{idPartit}")
	public String home(@PathVariable("idPartit") Long idPartit, Model model ,  HttpSession session) {
		System.out.println("ID del Partido: " + idPartit);
		model.addAttribute("idPartido", idPartit);
		String Dni = (String) session.getAttribute("userId");
		model.addAttribute("dni", Dni);
		return "index";
	}
	@PostMapping("/generate")
	public String generateQRCode(@ModelAttribute Tickets tickets, Model model, @RequestParam("idPartit") Integer idPartit, @RequestParam("dni") String dni) {
		try {
			tickets.setIdTicket(UUID.randomUUID().toString());
			tickets.setDataCompra(new Date(System.currentTimeMillis()));
			tickets.setDinsCamp(0);
			tickets.setIdPartit(idPartit);
			tickets.setDni(dni);
			String qrCode = qrCodeService.getQRCode(tickets.getIdTicket());
			model.addAttribute("qrcode", qrCode);
			ticketsRepository.save(tickets);
		} catch (Exception e) {
			// Manejo de errores si es necesario
			String error = "No se pudo insertar el ticket.";
			return "redirect:/?error=" + URLEncoder.encode(error, StandardCharsets.UTF_8);
		}
		return "index";
	}
	@GetMapping("/decode")
	public String decodeQRCode() {
		return "decode";
	}

	@PostMapping("/uploadQrCode")
	public String uploadQrCode(@RequestParam("qrCodeFile") MultipartFile qrCodeFile,
			RedirectAttributes redirectAttributes) {
		if (qrCodeFile.isEmpty()) {
			redirectAttributes.addFlashAttribute("errorMessage", "Please choose file to upload.");
			return "redirect:/decode";
		}
		try {
			String qrContent = qrCodeService.decodeQR(qrCodeFile.getBytes());
			redirectAttributes.addFlashAttribute("qrContent", qrContent);
			return "redirect:/decode";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/decode";
	}

}
