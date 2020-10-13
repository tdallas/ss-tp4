import pandas as pd
import matplotlib.pyplot as plt

best_day = pd.read_csv('data/distance-best-day.csv')
plt.plot(best_day['time'], best_day['distance'])

plt.xlabel('Tiempo [S]', fontsize=16)
plt.ylabel('Distancia [M]', fontsize=16)
plt.title('Distancia a de la nave a Marte')
plt.tight_layout()
plt.show()