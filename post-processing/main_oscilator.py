import pandas as pd
import matplotlib.pyplot as plt

oscilator = pd.read_csv('/home/tomas/itba/ss-tp4/out/oscillator-analytic.csv')
plt.plot(oscilator['time'], oscilator['position'])

plt.xlabel('Tiempo', fontsize=16)
plt.ylabel('Posición [m]', fontsize=16)
plt.title('Posición en función del tiempo para solución analítica')
plt.tight_layout()
plt.show()

oscilator_beeman = pd.read_csv('/home/tomas/itba/ss-tp4/out/oscillator-beeman.csv')
plt.plot(oscilator_beeman['time'], oscilator_beeman['position'])

plt.xlabel('Tiempo', fontsize=16)
plt.ylabel('Posición [m]', fontsize=16)
plt.title('Posición en función del tiempo para solución beeman')
plt.tight_layout()
plt.show()


oscilator_euler = pd.read_csv('/home/tomas/itba/ss-tp4/out/oscillator-euler.csv')
plt.plot(oscilator_euler['time'], oscilator_euler['position'])

plt.xlabel('Tiempo', fontsize=16)
plt.ylabel('Posición [m]', fontsize=16)
plt.title('Posición en función del tiempo para solución euler')
plt.tight_layout()
plt.show()


oscilator_gear = pd.read_csv('/home/tomas/itba/ss-tp4/out/oscillator-gear.csv')
plt.plot(oscilator_gear['time'], oscilator_gear['position'])

plt.xlabel('Tiempo', fontsize=16)
plt.ylabel('Posición [m]', fontsize=16)
plt.title('Posición en función del tiempo para solución gear')
plt.tight_layout()
plt.show()