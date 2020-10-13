import pandas as pd
import matplotlib.pyplot as plt

day = pd.read_csv('data/min-distance-for-days.csv')
plt.plot(day['day'], day['distance'])

plt.xlabel('Día', fontsize=16)
plt.ylabel('Distancia [M]', fontsize=16)
plt.title('Mínima distancia de la nave a Marte')
plt.tight_layout()
plt.show()