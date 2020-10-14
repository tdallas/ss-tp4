import matplotlib.pyplot as plt
from parser_xyz import XYZParser

parsed_data = XYZParser('out/0-spaceship-to-mars.xyz')

velocity = []
time = []

spaceship = parsed_data.get_particle_with_id(3)
for i in range(1, len(spaceship)):
    velocity.append(spaceship[i].get_velocity())
    time.append(spaceship[i].get_time_passed())

plt.plot(time, velocity)
plt.xlabel('Tiempo [S]', fontsize=16)
plt.ylabel('Velocidad [M/S]', fontsize=16)
plt.title('Velocidad de la nave hasta distancia mínima a Marte')
plt.tight_layout()
plt.show()