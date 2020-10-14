import pandas as pd
import matplotlib.pyplot as plt
from parser_xyz import XYZParser

parsed_data = XYZParser('out/0-spaceship-to-mars.xyz')
min_distance_mars = parsed_data.get_min_distance_between_particles(2, 3)
print(min_distance_mars)

# day = pd.read_csv('data/min-distance-for-days.csv')
# plt.plot(day['day'], day['distance'])
#
# plt.xlabel('Día', fontsize=16)
# plt.ylabel('Distancia [M]', fontsize=16)
# plt.title('Mínima distancia de la nave a Marte')
# plt.tight_layout()
# plt.show()