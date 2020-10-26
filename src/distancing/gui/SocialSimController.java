package distancing.gui;

import java.util.EnumMap;



import distancing.model.Person;
import distancing.model.Simulation;
import distancing.model.State;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;


public class SocialSimController {

	@FXML
	Pane world;
	
	@FXML
	Button startButton;

	@FXML
	Button stopButton;
	
	@FXML
	Button steptButton;
	
	@FXML
	Slider sizeSlider;
	
	@FXML
	Slider recoverySlider;
	
	@FXML
	Slider distanceSlider;
	
	@FXML
	TextField tickText;
	
	@FXML
	Pane chart;
	
	@FXML
	Pane histogram;
	
	Simulation sim;
	
	EnumMap<State, Rectangle> hrects = new EnumMap<State, Rectangle>(State.class);
	
	private Movemment clock;
	private class Movemment extends AnimationTimer {

		private long FRAME_PER_SEC = 50L;
		public long INTERVAL = 1000000000L / FRAME_PER_SEC;
		
		private long last = 0;
		private int ticks = 0;
		
		@Override
		public void handle(long now) {
			if(now - last > INTERVAL) {
				step();
				last = now;
				ticks();
				
			}
			
		}

		public int getTicks() {
			return ticks;
		}
		
		public void resetTicks() {
			ticks=0;
		}
		
		public void ticks() {
			ticks++;
		}
	}
	@FXML
	public void initialize() {
		//escucha cuando cambia
		sizeSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				setSize();				
			}
		});
		
		recoverySlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				setRecovery();		
			}
		});
		
		distanceSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				setDistance();		
			}
		});
		clock = new Movemment();
		world.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
	}
	
	@FXML
	public void reset() {
		stop();
		clock.resetTicks();
		tickText.setText(""+clock.getTicks());
		//Pasamos la población y el panel
		world.getChildren().clear();
		//histogram.getChildren().clear();
		//Limpia los gráficos
		chart.getChildren().clear();
		sim = new Simulation(world, 100);
		
		setSize();
		setRecovery();
		setDistance();
		
		int offset = 0;
		for(State s: State.values()) {
			Rectangle r = new Rectangle(60, 0, s.getColor());
			r.setTranslateX(offset);
			offset +=65;
			hrects.put(s, r);
			histogram.getChildren().add(r);
		}
		drawCharts();
	}
	
	/**
	 * método para asignar el tamaño del radio
	 */
	public void setSize() {
		Person.radius =(int) sizeSlider.getValue();
		sim.draw();
	}
	
	public void setRecovery() {
		Person.healtime = 50 * (int) recoverySlider.getValue();
	}
	
	public void setDistance() {
		Person.distance = (int) distanceSlider.getValue();
	}
	
	@FXML
	public void start() {
		clock.start();
		this.disableButton(true, false, true);
	}
	
	@FXML
	public void stop() {
		clock.stop();
		this.disableButton(false, true, false);
	}
	
	@FXML
	public void step() {
		sim.move();
		sim.feelBetter();
		sim.resolveCollisions();
		sim.draw();
		clock.ticks();
		tickText.setText(""+clock.getTicks());
		drawCharts();
	}
	
	
	/**
	 * 
	 * @param start activa o desactiva boton de empezar
	 * @param stop activa o desactiva boton de parar
	 * @param step activa o desactiva boton de paso
	 */
	public void disableButton(boolean start, boolean stop, boolean step) {
		startButton.setDisable(start);
		stopButton.setDisable(stop);
		steptButton.setDisable(step);
		
	}
	
	/**
	 * método dibuja gráficos
	 */
	public void drawCharts() {
		EnumMap<State, Integer> currentPop = new EnumMap<State, Integer>(State.class);
		for(Person p : sim.getPeople()) {
			if(!currentPop.containsKey(p.getState())) {
				currentPop.put(p.getState(), 0);
			}
			currentPop.put(p.getState(), 1+currentPop.get(p.getState()));
		}
		for(State state: hrects.keySet()) {
			if(currentPop.containsKey(state)) {
				hrects.get(state).setHeight(currentPop.get(state));
				//cambiamos la posición de la gráfica
				hrects.get(state).setTranslateY(30 + 100 - currentPop.get(state));
				
				Circle c = new Circle(1, state.getColor());
				c.setTranslateX(clock.getTicks() / 5.0);
				c.setTranslateY(130 - currentPop.get(state));
				chart.getChildren().add(c);
			}
		}
		if(!currentPop.containsKey(State.INFECTED)) {
			stop();
		}
	}
	
	
}
