import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

best_day = pd.read_csv('data/velocity-best-day.csv')

velocity = []
xVelocity = best_day['xVelocity'].values
yVelocity = best_day['yVelocity'].values

for i in range(xVelocity.size):
    velocity.append(np.sqrt(xVelocity[i]**2 + yVelocity[i]**2))

plt.plot(best_day['time'], velocity)

plt.xlabel('Tiempo [S]', fontsize=16)
plt.ylabel('Velocidad [M/S]', fontsize=16)
plt.title('Velocidad de la nave hasta distancia mínima a Marte')
plt.tight_layout()
plt.show()