package com.mkyong.web.controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mkyong.web.dao.MainDAO;
import com.mkyong.web.model.Connection;
import com.mkyong.web.model.Journey;
import com.mkyong.web.model.Location;
import com.mkyong.web.model.RouteMap;
import com.mkyong.web.model.UserRoute;
import com.mkyong.web.model.Users;
import com.mkyong.web.util.DijkstraAlgorithm;

@Controller
@Transactional
public class MainController {

	@Autowired
	private MainDAO mainDAO;

	/**
	 * both "normal login" and "login for update" shared this form.
	 * 
	 */
	/*
	 * @RequestMapping("/") public ModelAndView defaultpage(){ ModelAndView
	 * model = new ModelAndView(); model.setViewName("afterLogin"); return
	 * model; }
	 */

	/**
	 * both "normal login" and "login for update" shared this form.
	 * 
	 */
	@RequestMapping({ "/login", "/" })
	public @ResponseBody ModelAndView login(
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, HttpServletRequest request) {
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		ModelAndView model = new ModelAndView();
		if (null != error || "".equals(error)) {
			model.addObject("error", "Invalid username and password!");
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
			JSONObject object = new JSONObject();
			try {
				object.put("jsonmsg", "You've been logged out successfully.");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			model.addObject("jsonobj", object);
		}
		if (principal.equals("anonymousUser")) {
			model.setViewName("new_login");
		} else {
			model.setViewName("afterLogin");
			String username1 = ((UserDetails)principal).getUsername();
			HttpSession httpSession = request.getSession();
			httpSession.setAttribute("username",username1);
			model.addObject("username",username1);
		}
		return model;
	}

	@RequestMapping(value = "/algo", method = RequestMethod.GET)
	public @ResponseBody String shortestPathAlgo(
			@RequestParam(value = "source") String src,
			@RequestParam(value = "dest") String dest,HttpServletRequest request) {
		// ModelAndView model = new ModelAndView();
		String username = (String) request.getSession().getAttribute("username");
		Users user = mainDAO.getUserByUserName(username);
		StringBuilder output = new StringBuilder();
		if (user != null) {
			List<Connection> connections = mainDAO.getAllConnections();
			List<Location> locations = mainDAO.getAllLocations();
			Location source = mainDAO.getLocationByName(src);
			Location destination = mainDAO.getLocationByName(dest);
			RouteMap routeMap = new RouteMap(connections, locations);
			DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(routeMap);
			dijkstra.execute(source);
			LinkedList<Location> path = dijkstra.getPath(destination);
			UserRoute route = new UserRoute();
			route.setDestinationId(locations.get(8).getLocationId());
			route.setSourceId(locations.get(1).getLocationId());
			route.setUser(user);
			mainDAO.saveUserRoute(route);
			int count = 0;
			for (Location vertex : path) {
				Journey journey = new Journey();
				journey.setLocation(vertex);
				journey.setUserRoute(route);
				mainDAO.saveJourney(journey);
				count ++;
				output.append(journey.getLocation());
				if(count < path.size()){
					output.append(" ----> ");
				}
			}
		}
		return output.toString();
	}


	@RequestMapping(value = "/ownerPage", method = RequestMethod.GET)
	public ModelAndView ownerPage(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		String username = (String) request.getSession().getAttribute("username");
		model.addObject("username", username);
		model.setViewName("owner_Page");
		return model;
	}

	@RequestMapping(value = "/poolinPage", method = RequestMethod.GET)
	public ModelAndView poolinPage(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		String username = (String) request.getSession().getAttribute("username");
		model.addObject("username", username);
		model.setViewName("poolin_Page");
		return model;
	}

}