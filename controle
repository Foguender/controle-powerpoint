import pyautogui
from flask import Flask

app = Flask(__name__)

@app.route('/avancar')
def avancar():
    pyautogui.press('right') # Passa o slide para frente
    return "OK"

@app.route('/voltar')
def voltar():
    pyautogui.press('left')  # Volta o slide
    return "OK"

if __name__ == '__main__':
    # O host '0.0.0.0' permite que o celular encontre o PC na rede
    app.run(host='0.0.0.0', port=5000)
