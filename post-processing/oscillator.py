import pandas as pd
import matplotlib.pyplot as plt

analytic = pd.read_csv('out/oscillator-analytic.csv')
plt.plot(analytic['time'], analytic['position'], label='Analítica')

verlet = pd.read_csv('out/oscillator-verlet.csv')
plt.plot(verlet['time'], verlet['position'], label='Verlet')

beeman = pd.read_csv('out/oscillator-beeman.csv')
plt.plot(beeman['time'], beeman['position'], label='Beeman')

gear = pd.read_csv('out/oscillator-gear.csv')
plt.plot(gear['time'], gear['position'], label='GearPC')

plt.xlabel('Tiempo [S]', fontsize=16)
plt.ylabel('Posición [M]', fontsize=16)
plt.xlim(-0.25, 5.25)
plt.ylim(-1.1, 1.1)
plt.legend(title='Tipo de Integrador')
plt.title('Comparación de Integradores en Oscilador')
plt.tight_layout()
plt.show()