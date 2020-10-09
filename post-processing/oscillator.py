import pandas as pd
import matplotlib.pyplot as plt

analytic = pd.read_csv('out/oscillator-analytic.csv')
plt.plot(analytic['time'], analytic['position'], label='Analítica')

verlet = pd.read_csv('out/oscillator-verlet.csv')
plt.plot(verlet['time'], verlet['position'], label='Verlet')

beeman = pd.read_csv('out/oscillator-beeman.csv')
plt.plot(beeman['time'], beeman['position'], label='Beeman')

plt.xlabel('Tiempo [S]', fontsize=16)
plt.ylabel('Posición', fontsize=16)
plt.legend(title='Tipo de Integrador')
plt.show()