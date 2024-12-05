from flask import Blueprint, abort, flash,request,render_template,redirect
import requests
import json

router = Blueprint('router', __name__)
@router.route('/pruebas')
def ho():
    r = requests.get('http://localhost:8099/myapp/person/list')
    # #print(type(r.json()))
    # #print(r.json())
    data = r.json()  
    return render_template('index.html', lista = data["data"]) 
   
@router.route('/')
def home(): 
    r = requests.get('http://localhost:8099/myapp/person/list')
    data = r.json() 
    print("AAAAAAAAAAAAAAAAAAAAAaa", data)
    return render_template('fragmento/persona/lista.html', lista = data["data"]) 

@router.route('/admin')

def admin():
 
    return render_template('fragmento/inicial/admin.html') 


@router.route('/admin/person/list')
def list_person():
    r = requests.get('http://localhost:8099/myapp/person/list')
    data = r.json() 
    print("AAAAAAAAAAAAAAAAAAAAAaa", data)
    return render_template('fragmento/persona/lista.html', lista = data["data"]) 

@router.route('/admin/person/registro')
def view_register_person():
    # r = requests.get('http://localhost:8099/myapp/person/listType')
    # data = r.json() 
    # print(r.json())

    return render_template('fragmento/persona/registro.html', lista = ['CEDULA', 'PASAPORTE', 'RUC'])  

@router.route('/admin/person/edit/<id>')
def view_edit_person(id):
    # r = requests.get('http://localhost:8099/myapp/person/listType')
    # data = r.json() 
    # print(r.json())
    r = requests.get('http://localhost:8099/myapp/person/get/'+id)
    return render_template('fragmento/persona/editar.html', lista = ['CEDULA', 'PASAPORTE', 'RUC'], persona = r.json())  



@router.route('/admin/person/save', methods=['POST'])
def save_persona():

    form = request.form
    #dataF=form.to_dict()
    dataF = {"tipo":form ['tipo'], "nombre":form['nombre'],"apellido":form['apellido'],"dni":form['dni'],"numCelular":form['numCelular'],"sexo":form['sexo'],"fechaNacimiento":form['fechaNacimiento']}
    print(form.to_dict())
    r = requests.post('http://localhost:8099/myapp/person/save', json=dataF)
    dat = r.json()
    print(dat)
    if r.status_code == 200:
        
        return redirect('/admin/person/list')
    else:
        
        return redirect('/admin/person/list')


@router.route('/admin/energia/list')
def list_energia():
    r = requests.get('http://localhost:8099/myapp/energia/list')
    data = r.json() 
    print("AAAAAAAAAAAAAAAAAAAAAaa", data)
    return render_template('fragmento/energia/listaE.html', lista = data["data"]) 

@router.route('/admin/energia/registro')
def view_registro():
    r = requests.get('http://localhost:8099/myapp/energia/list')
    data = r.json() 
    print("AAAAAAAAAAAAAAAAAAAAAaa", data)
    return render_template('fragmento/energia/registroE.html', lista = data["data"]) 

@router.route('/admin/energia/edit/<id>')
def view_edit_registro(id):
    r = requests.get("http://localhost:8099/myapp/energia/list")
    data = r.json() 
    r1 = requests.get("http://localhost:8099/myapp/energia/get/"+id)
    data1 = r1.json() 
    print("AAAAAAAAAAAAAAAAAAAAAaa", data1)
    if(r1.status_code == 200):
        return render_template('fragmento/energia/editarE.html', lista = data["data"], energia = data1["data"]) 
    else:
        flash(data1["data"],category= 'Error')
        return redirect('/admin/energia/list')




@router.route('/admin/energia/save', methods=['POST'])
def save_registro():
    headers = {'Content-Type': 'application/json'}
    form = request.form
    #dataF=form.to_dict()

    dataF = {"nombre":form ['nombre'], "inversion":form['inversion'],"tiempoVida":form['tiempoVida'],"fechaInicio":form['fechaInicio'],"fechaFin":form['fechaFin'],"capacidadDiaria":form['capacidadDiaria']}
    print(dataF)
    r = requests.post('http://localhost:8099/myapp/energia/save', json=dataF)
    dat = r.json()
    print(dat)
    if r.status_code == 200:
        
        return redirect('/admin/energia/list')
    else:
        
        return redirect('/admin/energia/list')
    


@router.route('/admin/energia/update', methods=['POST'])
def update_registro():
    headers = {'Content-Type': 'application/json'}
    form = request.form
    #dataF=form.to_dict()

    dataF = {"id":form ['id'],"nombre":form ['nombre'], "inversion":form['inversion'],"tiempoVida":form['tiempoVida'],"fechaInicio":form['fechaInicio'],"fechaFin":form['fechaFin'],"capacidadDiaria":form['capacidadDiaria']}
    print(dataF)
    r = requests.post('http://localhost:8099/myapp/energia/update', data=json.dumps(dataF), headers=headers)
    dat = r.json()
    print(dat)
    if r.status_code == 200:
        
        return redirect('/admin/energia/list')
    else:
        
        return redirect('/admin/energia/list')
    #return render_template('fragmento/energia/listaE.html', lista = dat["data"])

##Cosas IVAN
##El buscar

@router.route('/admin/energia/search', methods=['POST'])
def search_energia():
    search_params = request.get_json()  # Cambio para manejar JSON en lugar de form data
    
    headers = {'Content-Type': 'application/json'}
    r = requests.post('http://localhost:8099/myapp/energia/search', 
                     json=search_params, 
                     headers=headers)
    
    return r.json()  # Devolver directamente el JSON para la petición AJAX

##El ordenar
@router.route('/admin/energia/sort', methods=['GET', 'POST'])
def sort_energia():
    if request.method == 'GET':
        # Render sort form
        return render_template('fragmento/energia/listaE.html')
    
    form = request.form
    sort_params = {
        "sortBy": form.get('sortBy'),
        "sortOrder": form.get('sortOrder'),
        "sortMethod": form.get('sortMethod')
    }
    
    headers = {'Content-Type': 'application/json'}
    r = requests.post('http://localhost:8099/myapp/energia/sort', json=sort_params, headers=headers)
    
    data = r.json()
    if r.status_code == 200:
        return render_template('fragmento/energia/listaE.html', lista=data['data'])
    else:
        flash('Error al ordenar', category='Error')
        return redirect('/admin/energia/list')
