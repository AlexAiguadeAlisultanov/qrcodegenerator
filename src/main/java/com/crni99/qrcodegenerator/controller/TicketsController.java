package com.crni99.qrcodegenerator.controller;

import com.crni99.qrcodegenerator.model.Partits;
import com.crni99.qrcodegenerator.model.Tickets;
import com.crni99.qrcodegenerator.model.Usuaris;
import com.crni99.qrcodegenerator.repository.PartitsRepository;
import com.crni99.qrcodegenerator.repository.TicketsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.crni99.qrcodegenerator.service.QRCodeService;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
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
	public String home(@PathVariable("idPartit") int idPartit, Model model, HttpSession session) {
		System.out.println("ID del Partit: " + idPartit);

		Optional<Partits> optionalPartido = repository.findById(idPartit);

		if (optionalPartido.isPresent()) {
			Partits partido = optionalPartido.get();
			// Guardar el precio y el nombre del partido en variables de sesión
			session.setAttribute("precioPartido", partido.getPreu()/100); // passem a euros ja que a la db esta en centims
			session.setAttribute("nombrePartido", partido.getNomPartit());

			model.addAttribute("partido", partido);
			model.addAttribute("idPartido", idPartit);

			String dni = (String) session.getAttribute("userId");
			model.addAttribute("dni", dni);

			return "index";
		} else {
			return "redirect:/error";
		}
	}
	@PostMapping("/generate")
	public String generateQRCode(@ModelAttribute Tickets tickets, Model model, @RequestParam("idPartit") Integer idPartit, HttpSession session, Usuaris usuario) {
		try {
			tickets.setIdTicket(UUID.randomUUID().toString());
			tickets.setDataCompra(new Date(System.currentTimeMillis()));
			tickets.setDinsCamp(0);
			tickets.setIdPartit(idPartit);
			String dniValue = (String) session.getAttribute("dniValue");
			if (dniValue != null) {
				tickets.setDni(dniValue);
			} else {
				String dni = String.valueOf(session.getAttribute("userId"));
				tickets.setDni(dni);
			}
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
	public String uploadQrCode(@RequestParam("qrContent") String qrContent, Model model, RedirectAttributes redirectAttributes) {
		System.out.println(qrContent);

		boolean qrValido = validarQrCode(qrContent);

		if (qrValido) {
			model.addAttribute("qrValido", true);
		} else {
			model.addAttribute("qrValido", false);
		}

		return "decode";
	}
	private boolean validarQrCode(String qrContent) {
		LocalDate currentDate = LocalDate.now();
		Date date = java.sql.Date.valueOf(currentDate);

		String idTicket = qrContent;

		Tickets ticket = ticketsRepository.findByIdTicket(idTicket);

		if (ticket != null) {
			Partits partits = repository.findById(ticket.getIdPartit()).orElse(null);
			System.out.println(partits.getDataPartit());
			System.out.println(currentDate);

			if (partits != null) {
				if (partits.getDataPartit().equals(date)) {
					System.out.println("El Partit és avui");
					return true;
				} else {
					System.out.println("El Partit no és avui");
					System.out.println("Data del partit: " + partits.getDataPartit());
					return false;
				}
			} else {
				return false;
			}
		} else {
			System.out.println("Error: Ticket no encontrado");
		}
		return false;
	}

	@PostMapping("/successfulPayment/{idTicket}")
	public String successfulPayment(@PathVariable String idTicket, Model model) {
		// Lógica para manejar el pago exitoso y generar el código QR
		try {
			String qrCode = qrCodeService.getQRCode(idTicket);
			model.addAttribute("qrcode", qrCode);
			return "index"; // Página de éxito con el código QR
		} catch (Exception e) {
			// Manejo de errores si es necesario
			return "error";
		}
	}


}
